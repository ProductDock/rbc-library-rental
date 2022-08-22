package com.productdock.library.rental.integration;

import com.productdock.library.rental.adapter.out.mongo.BookRentalStateRepository;
import com.productdock.library.rental.adapter.out.mongo.entity.BookRentalState;
import com.productdock.library.rental.domain.RentalStatus;
import com.productdock.library.rental.integration.kafka.KafkaTestBase;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.productdock.library.rental.data.provider.out.mongo.BookCopyRentalStateMother.defaultBookCopyRentalStateBuilder;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetBookRentalsApiTest extends KafkaTestBase {

    private static final String DEFAULT_ID = "2";
    private static final String FIRST_BOOK = "1";
    private static final String PATRON = "test1@productdock.com";
    private static final Date DATE_21_06_2022 = new Date(1655805600000L);
    private static final Date DATE_17_06_2022 = new Date(1655460000000L);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRentalStateRepository bookRentalStateRepository;

    public static MockWebServer mockUserProfilesBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockUserProfilesBackEnd = new MockWebServer();
        mockUserProfilesBackEnd.start(8085);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockUserProfilesBackEnd.shutdown();
    }

    @BeforeEach
    final void before() {
        bookRentalStateRepository.deleteAll();
    }

    @Test
    void shouldGetBookRecords() throws Exception {
        givenAnyRentalRecord();
        mockUserProfilesBackEnd.enqueue(new MockResponse()
                .setBody("[{\"fullName\": \"Test test\", \"image\": \"image\", \"email\": \"::email1::\"}, " +
                        "{\"fullName\": \"Test test\", \"image\": \"image\", \"email\": \"::email2::\"}]")
                .addHeader("Content-Type", "application/json"));

        var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSxxx");

        mockMvc.perform(get("/api/rental/book/" + FIRST_BOOK + "/rentals")
                        .with(jwt().jwt(jwt -> {
                            jwt.claim("email", PATRON);
                        })))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").value(hasSize(2)))
                .andExpect(jsonPath("$.[*].status",
                        containsInAnyOrder(RentalStatus.RENTED.toString(), RentalStatus.RESERVED.toString())))
                .andExpect(jsonPath("$.[*].user.email",
                        containsInAnyOrder("::email1::", "::email1::")))
                .andExpect(jsonPath("$.[*].date",
                        containsInAnyOrder(
                                dateFormatter.format(ZonedDateTime.ofInstant(DATE_17_06_2022.toInstant(), ZoneOffset.UTC)),
                                dateFormatter.format(ZonedDateTime.ofInstant(DATE_21_06_2022.toInstant(), ZoneOffset.UTC))
                        )));
    }

    private void givenAnyRentalRecord() {
        var reservedInteraction = defaultBookCopyRentalStateBuilder()
                .userEmail("::email1::")
                .status(RentalStatus.RESERVED)
                .date(DATE_17_06_2022)
                .build();

        var rentedInteraction = defaultBookCopyRentalStateBuilder()
                .userEmail("::email1::")
                .status(RentalStatus.RENTED)
                .date(DATE_21_06_2022)
                .build();

        var rentalRecord = BookRentalState.builder()
                .id(DEFAULT_ID)
                .bookId(FIRST_BOOK)
                .bookCopyRentalState(reservedInteraction)
                .bookCopyRentalState(rentedInteraction)
                .build();

        bookRentalStateRepository.save(rentalRecord);
    }
}
