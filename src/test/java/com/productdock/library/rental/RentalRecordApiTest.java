package com.productdock.library.rental;

import com.productdock.library.rental.adapter.out.kafka.RentalRecordsMessage;
import com.productdock.library.rental.adapter.out.mongo.RentalRecordEntityRepository;
import com.productdock.library.rental.data.provider.KafkaTestBase;
import com.productdock.library.rental.data.provider.KafkaTestConsumer;
import com.productdock.library.rental.domain.RentalActionType;
import com.productdock.library.rental.domain.RentalStatus;
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

import static com.productdock.library.rental.data.provider.BookInteractionMother.defaultBookInteractionBuilder;
import static com.productdock.library.rental.data.provider.RentalRecordEntityMother.defaultRentalRecordEntityBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RentalRecordApiTest extends KafkaTestBase {

    public static final String FIRST_BOOK = "1";
    public static final String TEST_FILE = "testRecord.txt";
    public static final String PATRON_1 = "test1@productdock.com";
    public static final String PATRON_2 = "test2@productdock.com";
    public static final String PATRON_3 = "test3@productdock.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RentalRecordEntityRepository rentalRecordRepository;

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
    void shouldCreateThreeRentalRecords_whenThreeDifferentUsersRentABook() throws Exception {
        makeRentalRequest(RentalActionType.RENT)
                .andExpect(status().isOk());
        makeRentalRequest(RentalActionType.RENT, PATRON_2)
                .andExpect(status().isOk());
        makeRentalRequest(RentalActionType.RENT, PATRON_3)
                .andExpect(status().isOk());

        await()
                .atMost(Duration.ofSeconds(5))
                .until(ifFileExists(TEST_FILE));

        RentalRecordsMessage rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId()).isEqualTo(FIRST_BOOK);
        assertThat(rentalRecordsMessage.getRentalRecords()).hasSize(3);
    }

    @Test
    void shouldCreateReservationRecord() throws Exception {
        makeRentalRequest(RentalActionType.RESERVE)
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
        makeRentalRequest(RentalActionType.RENT)
                .andExpect(status().isOk());
        makeRentalRequest(RentalActionType.RENT).
                andExpect(status().isBadRequest());

        await()
                .atMost(Duration.ofSeconds(4))
                .until(ifFileExists(TEST_FILE));

        var rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId()).isEqualTo(FIRST_BOOK);
        assertThat(rentalRecordsMessage.getRentalRecords()).hasSize(1);
    }

    @Test
    void shouldCreateReturnBookRecord_whenReturningABookAlreadyRentedByUser() throws Exception {
        makeRentalRequest(RentalActionType.RENT)
                .andExpect(status().isOk());
        makeRentalRequest(RentalActionType.RETURN)
                .andExpect(status().isOk());
        await()
                .atMost(Duration.ofSeconds(4))
                .until(ifFileExists(TEST_FILE));

        var rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId()).isEqualTo(FIRST_BOOK);
        assertThat(rentalRecordsMessage.getRentalRecords()).isEmpty();
    }

    @Test
    void shouldCreateRentBookRecord_whenRentingABookAlreadyReservedByUser() throws Exception {
        makeRentalRequest(RentalActionType.RESERVE)
                .andExpect(status().isOk());
        makeRentalRequest(RentalActionType.RENT)
                .andExpect(status().isOk());
        await()
                .atMost(Duration.ofSeconds(4))
                .until(ifFileExists(TEST_FILE));

        var rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId()).isEqualTo(FIRST_BOOK);
        assertThat(rentalRecordsMessage.getRentalRecords()).hasSize(1);
    }

    @Test
    void shouldReturnBadRequest_whenReservingABookAlreadyReservedByUser() throws Exception {
        makeRentalRequest(RentalActionType.RESERVE)
                .andExpect(status().isOk());
        makeRentalRequest(RentalActionType.RESERVE)
                .andExpect(status().isBadRequest());

        await()
                .atMost(Duration.ofSeconds(4))
                .until(ifFileExists(TEST_FILE));

        var rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId()).isEqualTo(FIRST_BOOK);
        assertThat(rentalRecordsMessage.getRentalRecords()).hasSize(1);
    }

    @Test
    void shouldCancelReservation_whenCancelingABookAlreadyReservedByUser() throws Exception {
        makeRentalRequest(RentalActionType.RESERVE)
                .andExpect(status().isOk());
        makeRentalRequest(RentalActionType.CANCEL_RESERVATION)
                .andExpect(status().isOk());
        await()
                .atMost(Duration.ofSeconds(4))
                .until(ifFileExists(TEST_FILE));

        var rentalRecordsMessage = getRentalRecordsMessageFrom(TEST_FILE);
        assertThat(rentalRecordsMessage.getBookId()).isEqualTo(FIRST_BOOK);
        assertThat(rentalRecordsMessage.getRentalRecords()).isEmpty();
    }

    @Test
    void shouldCancelReservation_whenReservationExpires() throws Exception {
        makeRentalRequest(RentalActionType.RESERVE)
                .andExpect(status().isOk());

        await()
                .atMost(Duration.ofSeconds(8))
                .until(reservationCanceled(FIRST_BOOK));

        mockMvc.perform(get("/api/rental/record/" + FIRST_BOOK)
                        .with(jwt().jwt(jwt -> jwt.claim("email", PATRON_1))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").value(hasSize(0)));
    }

    @Test
    void shouldReturnBadRequest_whenReturningABookNotRentedByUser() throws Exception {
        makeRentalRequest(RentalActionType.RETURN)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenCancelingReservationOfABookThatNotReservedByUser() throws Exception {
        makeRentalRequest(RentalActionType.CANCEL_RESERVATION)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetBookRecords() throws Exception {
        givenAnyRentalRecord();

        mockMvc.perform(get("/api/rental/record/" + FIRST_BOOK)
                        .with(jwt().jwt(jwt -> jwt.claim("email", PATRON_1))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").value(hasSize(2)))
                .andExpect(jsonPath("$.[*].status",
                        containsInAnyOrder(RentalStatus.RENTED.toString(), RentalStatus.RESERVED.toString())))
                .andExpect(jsonPath("$.[*].email",
                        containsInAnyOrder("default@gmail.com", "::email::")));

    }

    private void givenAnyRentalRecord() {
        var returnedInteraction = defaultBookInteractionBuilder().userEmail("::email::").status(RentalStatus.RESERVED).build();

        var rentalRecord = defaultRentalRecordEntityBuilder()
                .interaction(returnedInteraction)
                .build();
        rentalRecordRepository.save(rentalRecord);
    }


    private ResultActions makeRentalRequest(RentalActionType action, String email) throws Exception {
        return mockMvc.perform(post("/api/rental/book/"+FIRST_BOOK+"/action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"rentalAction\": \"" + action + "\"" +
                                "}").with(jwt().jwt(jwt -> jwt.claim("email", email))))
                .andDo(print());
    }

    private ResultActions makeRentalRequest(RentalActionType action) throws Exception {
        return makeRentalRequest(action, PATRON_1);
    }

    private Callable<Boolean> ifFileExists(String testFile) {
        Callable<Boolean> checkForFile = () -> {
            File f = new File(testFile);
            return f.isFile();
        };
        return checkForFile;
    }

    private Callable<Boolean> reservationCanceled(String bookId) {
        Callable<Boolean> checkIfCanceled = () -> {
            var records = rentalRecordRepository.findByBookId(bookId);
            if (records.isEmpty()) {
                return false;
            }
            return records.get().getInteractions().size() == 0;
        };
        return checkIfCanceled;
    }

    private RentalRecordsMessage getRentalRecordsMessageFrom(String testFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(testFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        var rentalRecordsMessage = (RentalRecordsMessage) objectInputStream.readObject();
        objectInputStream.close();
        return rentalRecordsMessage;
    }
}
