package com.productdock.library.rental.record;

import com.productdock.library.rental.consumer.KafkaTestConsumer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
class RecordApiTest {

    public static final String FIRST_BOOK = "1";
    public static final String TEST_FILE = "testRecord.txt";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private KafkaTestConsumer consumer;

    @Value("${spring.kafka.topic.rental-record-topic}")
    private String topic;

    @BeforeEach
    final void before() {
        recordRepository.deleteAll();
    }

    @AfterEach
    final void after() {
        File f = new File("TEST_FILE");
        f.delete();
    }

    @Test
    @WithMockUser
    void postRentRecord_whenTheyAreSentThroughKafka() throws Exception {
        mockApiRequest("RENT");
        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(checkForFile);

        RecordEntity recordEntity = getRecordEntityFromConsumersFile(TEST_FILE);
        assertThat(recordEntity.getBookId().equals(FIRST_BOOK));
        assertThat(recordEntity.getRents().get(0)).isNotNull();
    }

    @Test
    @WithMockUser
    void postTwoRentRecords_whenTheSecondFails() throws Exception {
        mockApiRequest("RENT");
        mockBadApiRequest("RENT");
        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(checkForFile);

        RecordEntity recordEntity = getRecordEntityFromConsumersFile(TEST_FILE);
        assertThat(recordEntity.getBookId().equals(FIRST_BOOK));
        assertThat(recordEntity.getRents().get(0)).isNotNull();
    }

    @Test
    @WithMockUser
    void postRentAndReturnRecord_WhenTheBookIsReturned() throws Exception{
        mockApiRequest("RENT");
        mockApiRequest("RETURN");
        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(checkForFile);

        RecordEntity recordEntity = getRecordEntityFromConsumersFile(TEST_FILE);
        assertThat(recordEntity.getBookId().equals(FIRST_BOOK));
        assertThat(recordEntity.getRents()).isEmpty();
    }

    @Test
    @WithMockUser
    void postReserveRecord_whenTheyAreSentThroughKafka() throws Exception {
        mockApiRequest("RESERVE");
        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(checkForFile);

        RecordEntity recordEntity = getRecordEntityFromConsumersFile(TEST_FILE);
        assertThat(recordEntity.getBookId().equals(FIRST_BOOK));
        assertThat(recordEntity.getReservations().get(0)).isNotNull();
    }


    private void mockBadApiRequest(String request) throws Exception {
        mockMvc.perform(post("/api/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"bookId\": \""+FIRST_BOOK+"\",\n" +
                                "    \"bookStatus\": \""+request+"\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private void mockApiRequest(String request) throws Exception {
        mockMvc.perform(post("/api/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"bookId\": \""+FIRST_BOOK+"\",\n" +
                                "    \"bookStatus\": \""+request+"\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
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

    private RecordEntity getRecordEntityFromConsumersFile(String testFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(testFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        var recordEntity = (RecordEntity) objectInputStream.readObject();
        objectInputStream.close();
        return recordEntity;
    }
}