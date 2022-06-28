package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetBookRentalsServiceShould {

    public static final String BOOK_ID = "1";
    private static final Optional<BookRentals> ANY_BOOK_RENTALS = Optional.of(mock(BookRentals.class));

    @InjectMocks
    private GetBookRentalsService getBookRentalsService;

    @Mock
    private BookRentalsPersistenceOutPort rentalRecordRepository;

    @Test
    void getBookRentalRecords_whenMissingRecords() {
        given(rentalRecordRepository.findByBookId(BOOK_ID)).willReturn(Optional.empty());

        var bookRecords = getBookRentalsService.getBookCopiesRentalState(BOOK_ID);

        assertThat(bookRecords).isEmpty();
    }

    @Test
    void getBookRentalRecords() {
        given(rentalRecordRepository.findByBookId(BOOK_ID)).willReturn(ANY_BOOK_RENTALS);

        var bookRecords = getBookRentalsService.getBookCopiesRentalState(BOOK_ID);

        assertThat(ANY_BOOK_RENTALS).isPresent();
        assertThat(bookRecords).isEqualTo(ANY_BOOK_RENTALS.get().getBookCopiesRentalState());
    }
}
