//package com.productdock.library.rental.service;
//
//import com.productdock.library.rental.data.provider.KafkaTestBase;
//import com.productdock.library.rental.data.provider.KafkaTestConsumer;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.time.Duration;
//import java.util.concurrent.Callable;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.testcontainers.shaded.org.awaitility.Awaitility.await;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
//class RentalRecordApiTest extends KafkaTestBase {
//
//    public static final String FIRST_BOOK = "1";
//    public static final String TEST_FILE = "testRecord.txt";
//    private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImV4YW1wbGVAcHJvZHVjdGRvY2suY29tIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.ElsZ_Vc_4O9YlL6QO85hjxSdiJ8S41HodjOIUydcGH4";
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private MockRestServiceServer mockServer;
//
//    @Autowired
//    private RentalRecordRepository rentalRecordRepository;
//
//    @Autowired
//    private KafkaTestConsumer consumer;
//
//    @Value("${spring.kafka.topic.book-status}")
//    private String topic;
//
//    @BeforeEach
//    final void before() {
//        rentalRecordRepository.deleteAll();
//    }
//
//    @AfterEach
//    final void after() {
//        File f = new File(TEST_FILE);
//        f.delete();
//    }
//
////    @BeforeEach
////    final void beforeAll() throws ParseException {
//////        JWT jwtToken = JWTParser.parse(token);
////        Jwt jwtToken = Jwt.tokenValue(token).build();
////
////        System.out.println(jwtToken.getTokenValue());
////        Authentication authentication = new JwtAuthenticationToken(jwtToken);
////        SecurityContextHolder.getContext().setAuthentication(authentication);
////    }
//
//    @Test
//    @WithMockUser
//    void postRentRecord_whenTheyAreSentThroughKafka() throws Exception {
//        mockApiRequest(RentalStatus.RENTED);
//        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
//        await()
//                .atMost(Duration.ofSeconds(20))
//                .until(checkForFile);
//
//        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFromConsumersFile(TEST_FILE);
//        assertThat(rentalRecordsMessage.getBookId().equals(FIRST_BOOK));
//        assertThat(rentalRecordsMessage.getRentalRecords()).isNotNull();
//    }
//
//    @Test
//    @WithMockUser
//    void postTwoRentRecords_whenTheSecondFails() throws Exception {
//        mockApiRequest(RentalStatus.RENTED);
//        assertThatThrownBy(() -> mockBadApiRequest(RentalStatus.RENTED))
//                .getCause()
//                .isInstanceOf(RuntimeException.class);
//
//        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
//        await()
//                .atMost(Duration.ofSeconds(20))
//                .until(checkForFile);
//
//        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFromConsumersFile(TEST_FILE);
//        assertThat(rentalRecordsMessage.getBookId().equals(FIRST_BOOK));
//        assertThat(rentalRecordsMessage.getRentalRecords().get(0)).isNotNull();
//    }
//
//    @Test
//    @WithMockUser
//    void postRentAndReturnRecord_WhenTheBookIsReturned() throws Exception {
//        mockApiRequest(RentalStatus.RENTED);
//        mockApiRequest(RentalStatus.RETURNED);
//        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
//        await()
//                .atMost(Duration.ofSeconds(20))
//                .until(checkForFile);
//
//        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFromConsumersFile(TEST_FILE);
//        assertThat(rentalRecordsMessage.getBookId().equals(FIRST_BOOK));
//        assertThat(rentalRecordsMessage.getRentalRecords()).isEmpty();
//    }
//
//    @Test
//    @WithMockUser
//    void postReserveRecord_whenTheyAreSentThroughKafka() throws Exception {
//        mockApiRequest(RentalStatus.RESERVED);
//        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
//        await()
//                .atMost(Duration.ofSeconds(20))
//                .until(checkForFile);
//
//        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFromConsumersFile(TEST_FILE);
//        assertThat(rentalRecordsMessage.getBookId().equals(FIRST_BOOK));
//        assertThat(rentalRecordsMessage.getRentalRecords().get(0)).isNotNull();
//    }
//
//    @Test
//    @WithMockUser
//    void postTwoReserveRecords_whenTheSecondFails() throws Exception {
//        mockApiRequest(RentalStatus.RESERVED);
//        assertThatThrownBy(() -> mockBadApiRequest(RentalStatus.RESERVED))
//                .getCause()
//                .isInstanceOf(RuntimeException.class);
//
//        Callable<Boolean> checkForFile = ifFileExists(TEST_FILE);
//        await()
//                .atMost(Duration.ofSeconds(20))
//                .until(checkForFile);
//
//        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFromConsumersFile(TEST_FILE);
//        assertThat(rentalRecordsMessage.getBookId().equals(FIRST_BOOK));
//        assertThat(rentalRecordsMessage.getRentalRecords().get(0)).isNotNull();
//    }
//
//    @Test
//    @WithMockUser
//    void postReturnRecord_whenTheRequestFails() throws Exception {
//        assertThatThrownBy(() -> mockApiRequest(RentalStatus.RETURNED))
//                .getCause()
//                .isInstanceOf(RuntimeException.class);
//    }
//
//    private void mockBadApiRequest(RentalStatus request) throws Exception {
////        RentalRequest rentalRequest = new RentalRequest("1", RentalStatus.RENTED);
////        restTemplate.postForEntity("/api/rental/record", rentalRequest);
//
//
////        mockServer.perform(post("/api/rental/record")
////                        .header("Authorization", "Bearer " + token)
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content("{\n" +
////                                "    \"bookId\": \"" + FIRST_BOOK + "\",\n" +
////                                "    \"requestedStatus\": \"" + request + "\"\n" +
////                                "}"))
////                .andDo(print())
////                .andExpect(status().isBadRequest());
//    }
//
//    private void mockApiRequest(RentalStatus request) throws Exception {
////        webTestClient.post()
////                .uri("/api/rental/record")
////                .header("Authorization", "Bearer " + token)
////                .bodyValue("{\n" +
////                        "    \"bookId\": \"" + FIRST_BOOK + "\",\n" +
////                        "    \"requestedStatus\": \"" + request + "\"\n" +
////                        "}")
////                .exchange()
////                .expectStatus().isOk();
//
//        mockMvc.perform(post("/api/rental/record")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\n" +
//                                "    \"bookId\": \"" + FIRST_BOOK + "\",\n" +
//                                "    \"requestedStatus\": \"" + request + "\"\n" +
//                                "}"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    private Callable<Boolean> ifFileExists(String testFile) {
//        Callable<Boolean> checkForFile = new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                File f = new File(testFile);
//                return f.isFile();
//            }
//        };
//        return checkForFile;
//    }
//
//    private RentalRecordsMessage getRentalRecordsMessageFromConsumersFile(String testFile) throws IOException, ClassNotFoundException {
//        FileInputStream fileInputStream = new FileInputStream(testFile);
//        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//        var rentalRecordsMessage = (RentalRecordsMessage) objectInputStream.readObject();
//        objectInputStream.close();
//        return rentalRecordsMessage;
//    }
//}
