package com.productdock.library.rental.service;

import com.productdock.library.rental.data.provider.KafkaTestBase;
import com.productdock.library.rental.data.provider.KafkaTestConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RentalRecordApiTest extends KafkaTestBase {

    public static final String FIRST_BOOK = "1";
    public static final String TEST_FILE = "testRecord.txt";
    public static final String PATRON = "test@productdock.com";

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
    void shouldCreateReservationRecord() throws Exception {
        makeRentalRequest(RentalStatus.RESERVED)
                .andExpect(status().isOk());

        await()
                .atMost(Duration.ofSeconds(5))
                .until(ifFileExists(TEST_FILE));

        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId()).isEqualTo(FIRST_BOOK);
        assertThat(rentalRecordsMessage.getRentalRecords()).isNotNull();
    }

    @Test
    void shouldReturnBadRequest_whenRentingABookAlreadyRentedByUser() throws Exception {
        makeRentalRequest(RentalStatus.RENTED)
                .andExpect(status().isOk());
        makeRentalRequest(RentalStatus.RENTED).
                andExpect(status().isBadRequest());

        var checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(4))
                .until(checkForFile);

        var rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId()).isEqualTo(FIRST_BOOK);
        assertThat(rentalRecordsMessage.getRentalRecords().get(0)).isNotNull();
    }

    @Test
    void shouldCreateReturnBookRecord_whenReturningABookAlreadyRentedByUser() throws Exception {
        makeRentalRequest(RentalStatus.RENTED)
                .andExpect(status().isOk());
        makeRentalRequest(RentalStatus.RETURNED)
                .andExpect(status().isOk());
        var checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(4))
                .until(checkForFile);

        var rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId()).isEqualTo(FIRST_BOOK);
        assertThat(rentalRecordsMessage.getRentalRecords()).isEmpty();
    }

    @Test
    void shouldReturnBadRequest_whenReservingABookAlreadyReservedByUser() throws Exception {
        makeRentalRequest(RentalStatus.RESERVED)
                .andExpect(status().isOk());
        makeRentalRequest(RentalStatus.RESERVED)
                .andExpect(status().isBadRequest());

        var checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(4))
                .until(checkForFile);

        var rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId()).isEqualTo(FIRST_BOOK);
        assertThat(rentalRecordsMessage.getRentalRecords().get(0)).isNotNull();
    }

    @Test
    void shouldReturnBadRequest_whenReturningABookNotRentedByUser() throws Exception {
        makeRentalRequest(RentalStatus.RETURNED)
                .andExpect(status().isBadRequest());
    }

    private ResultActions makeRentalRequest(RentalStatus request) throws Exception {
        return mockMvc.perform(post("/api/rental/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"bookId\": \"" + FIRST_BOOK + "\",\n" +
                                "    \"requestedStatus\": \"" + request + "\"\n" +
                                "}").with(jwt().jwt(jwt -> jwt.claim("email", PATRON))))
                .andDo(print());
    }

    private Callable<Boolean> ifFileExists(String testFile) {
        Callable<Boolean> checkForFile = () -> {
            File f = new File(testFile);
            return f.isFile();
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
