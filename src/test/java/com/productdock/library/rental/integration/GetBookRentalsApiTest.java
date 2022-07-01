package com.productdock.library.rental.integration;

import com.productdock.library.rental.adapter.out.mongo.BookRentalStateRepository;
import com.productdock.library.rental.domain.RentalStatus;
import com.productdock.library.rental.integration.kafka.KafkaTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.productdock.library.rental.data.provider.out.mongo.BookCopyRentalStateMother.defaultBookCopyRentalStateBuilder;
import static com.productdock.library.rental.data.provider.out.mongo.BookRentalStateMother.defaultBookRentalStateBuilder;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetBookRentalsApiTest extends KafkaTestBase{

    private static final String FIRST_BOOK = "1";
    private static final String PATRON = "test1@productdock.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRentalStateRepository bookRentalStateRepository;

    @BeforeEach
    final void before() {
        bookRentalStateRepository.deleteAll();
    }

    @Test
    void shouldGetBookRecords() throws Exception {
        givenAnyRentalRecord();

        mockMvc.perform(get("/api/rental/book/" + FIRST_BOOK + "/rentals")
                        .with(jwt().jwt(jwt -> jwt.claim("email", PATRON))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").value(hasSize(2)))
                .andExpect(jsonPath("$.[*].status",
                        containsInAnyOrder(RentalStatus.RENTED.toString(), RentalStatus.RESERVED.toString())))
                .andExpect(jsonPath("$.[*].email",
                        containsInAnyOrder("default@gmail.com", "::email::")));

    }

    private void givenAnyRentalRecord() {
        var returnedInteraction = defaultBookCopyRentalStateBuilder()
                .userEmail("::email::")
                .status(RentalStatus.RESERVED)
                .build();

        var rentalRecord = defaultBookRentalStateBuilder()
                .bookCopyRentalState(returnedInteraction)
                .build();
        bookRentalStateRepository.save(rentalRecord);
    }
}
