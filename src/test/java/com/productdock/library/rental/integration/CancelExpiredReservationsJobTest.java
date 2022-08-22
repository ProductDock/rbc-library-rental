package com.productdock.library.rental.integration;


import com.productdock.library.rental.adapter.out.mongo.BookRentalStateRepository;
import com.productdock.library.rental.domain.RentalActionType;
import com.productdock.library.rental.domain.UserProfile;
import com.productdock.library.rental.integration.kafka.KafkaTestBase;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.web.http.SecurityHeaders.bearerToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CancelExpiredReservationsJobTest extends KafkaTestBase {

    private static final String FIRST_BOOK = "1";
    private static final String PATRON = "test@productdock.com";

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
    void shouldCancelReservation_whenReservationExpires() throws Exception {
        makeRentalRequest(RentalActionType.RESERVE)
                .andExpect(status().isOk());
        mockUserProfilesBackEnd.enqueue(new MockResponse()
                .setBody("[]")
                .addHeader("Content-Type", "application/json"));

        await()
                .atMost(Duration.ofSeconds(8))
                .until(reservationCanceled(FIRST_BOOK));

        mockMvc.perform(get("/api/rental/book/" + FIRST_BOOK + "/rentals")
                        .with(jwt().jwt(jwt -> jwt.claim("email", PATRON))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").value(hasSize(0)));
    }

    private ResultActions makeRentalRequest(RentalActionType action) throws Exception {
        return mockMvc.perform(post("/api/rental/book/" + FIRST_BOOK + "/action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"rentalAction\": \"" + action + "\"" +
                                "}").with(jwt().jwt(jwt -> jwt.claim("email", PATRON))))
                .andDo(print());
    }

    private Callable<Boolean> reservationCanceled(String bookId) {
        Callable<Boolean> checkIfCanceled = () -> {
            var records = bookRentalStateRepository.findByBookId(bookId);
            if (records.isEmpty()) {
                return false;
            }
            return records.get().getBookCopiesRentalState().size() == 0;
        };
        return checkIfCanceled;
    }

}
