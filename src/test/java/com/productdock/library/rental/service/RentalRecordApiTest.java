package com.productdock.library.rental.service;

import com.productdock.library.rental.data.provider.KafkaTestBase;
import com.productdock.library.rental.data.provider.KafkaTestConsumer;
import lombok.NonNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RentalRecordApiTest extends KafkaTestBase {

    public static final String FIRST_BOOK = "1";
    public static final String TEST_FILE = "testRecord.txt";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RentalRecordRepository rentalRecordRepository;

    @Autowired
    private KafkaTestConsumer consumer;

    @Value("${spring.kafka.topic.book-status}")
    private String topic;

    @BeforeEach
    final void before() {
        rentalRecordRepository.deleteAll();
    }

    @AfterEach
    final void after() {
        File f = new File(TEST_FILE);
        f.delete();
    }

    @Test
    void postRentRecord_whenTheyAreSentThroughKafka() throws Exception {
        makeRentalRequest(RentalStatus.RESERVED, "test@productdock.com")
                .andExpect(status().isOk());

        await()
                .atMost(Duration.ofSeconds(5))
                .until(ifFileExists(TEST_FILE));

        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId().equals(FIRST_BOOK));
        assertThat(rentalRecordsMessage.getRentalRecords()).isNotNull();
    }

    @Test
    void postTwoRentRecords_whenTheSecondFails() throws Exception {
        makeRentalRequest(RentalStatus.RENTED, "test@productdock.com")
                .andExpect(status().isOk());
        assertThatThrownBy(() -> makeRentalRequest(RentalStatus.RENTED, "test@productdock.com").andExpect(status().isBadRequest()))
                .getCause()
                .isInstanceOf(RuntimeException.class);

        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(checkForFile);

        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId().equals(FIRST_BOOK));
        assertThat(rentalRecordsMessage.getRentalRecords().get(0)).isNotNull();
    }

    @Test
    void postRentAndReturnRecord_WhenTheBookIsReturned() throws Exception {
        makeRentalRequest(RentalStatus.RENTED, "test@productdock.com")
                .andExpect(status().isOk());
        makeRentalRequest(RentalStatus.RETURNED, "test@productdock.com")
                .andExpect(status().isOk());
        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(checkForFile);

        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId().equals(FIRST_BOOK));
        assertThat(rentalRecordsMessage.getRentalRecords()).isEmpty();
    }

    @Test
    void postReserveRecord_whenTheyAreSentThroughKafka() throws Exception {
        makeRentalRequest(RentalStatus.RESERVED, "test@productdock.com")
                .andExpect(status().isOk());
        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(checkForFile);

        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId().equals(FIRST_BOOK));
        assertThat(rentalRecordsMessage.getRentalRecords().get(0)).isNotNull();
    }

    @Test
    void postTwoReserveRecords_whenTheSecondFails() throws Exception {
        makeRentalRequest(RentalStatus.RESERVED, "test@productdock.com");

        assertThatThrownBy(() -> makeRentalRequest(RentalStatus.RESERVED, "test@productdock.com")
                .andExpect(status().isBadRequest()))
                .getCause()
                .isInstanceOf(RuntimeException.class);

        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(checkForFile);

        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId().equals(FIRST_BOOK));
        assertThat(rentalRecordsMessage.getRentalRecords().get(0)).isNotNull();
    }

    @Test
    void postReturnRecord_whenTheRequestFails() throws Exception {
        assertThatThrownBy(() -> makeRentalRequest(RentalStatus.RETURNED, "test@productdock.com")
                .andExpect(status().isBadRequest()))
                .getCause()
                .isInstanceOf(RuntimeException.class);
    }

    private ResultActions makeRentalRequest(RentalStatus request, String patron) throws Exception {
        return mockMvc.perform(post("/api/rental/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"bookId\": \"" + FIRST_BOOK + "\",\n" +
                                "    \"requestedStatus\": \"" + request + "\"\n" +
                                "}").with(jwt().jwt(jwt -> jwt.claim("email", patron))))
                .andDo(print());
    }

    private Callable<Boolean> ifFileExists(String testFile) {
        Callable<Boolean> checkForFile = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                File f = new File(testFile);
                return f.isFile();
            }
        };
        return checkForFile;
    }

    private RentalRecordsMessage getRentalRecordsMessageFrom(String testFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(testFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        var rentalRecordsMessage = (RentalRecordsMessage) objectInputStream.readObject();
        objectInputStream.close();
        return rentalRecordsMessage;
    }
}
