package com.productdock.library.rental.adapter.out.kafka.mapper;

import com.productdock.library.rental.adapter.out.kafka.BookRentalStatusChanged;
import com.productdock.library.rental.domain.BookRentals;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.productdock.library.rental.data.provider.domain.BookRentalsMother.bookRentalsWithRentRequest;
import static org.assertj.core.api.Assertions.assertThat;


class BookRentalsMessageMapperShould {

    private final BookRentalsMessageMapper bookRentalsMessageMapper = Mappers.getMapper(BookRentalsMessageMapper.class);

    @Test
    void mapBookRentalsToRentalRecordsMessage() {
        var bookRentalRecord = bookRentalsWithRentRequest();

        var rentalRecordsMessage = bookRentalsMessageMapper.toMessage(bookRentalRecord);

        assertThat(bookRentalRecord.getBookId()).isEqualTo(rentalRecordsMessage.getBookId());
        assertThatRecordsAreMatching(bookRentalRecord.getBookCopiesRentalState(), rentalRecordsMessage.getRentalRecords());
    }

    private void assertThatRecordsAreMatching(List<BookRentals.BookCopyRentalState> bookCopiesRentalState,
                                              List<BookRentalStatusChanged.RentalRecord> rentalRecordRequests) {
        assertThat(bookCopiesRentalState).hasSameSizeAs(rentalRecordRequests);
        var bookCopy = bookCopiesRentalState.get(0);
        var rentalRecordRequest = rentalRecordRequests.get(0);
        assertThatBookCopyIsMatching(bookCopy, rentalRecordRequest);
    }

    private void assertThatBookCopyIsMatching(BookRentals.BookCopyRentalState bookCopiesRentalState,
                                              BookRentalStatusChanged.RentalRecord rentalRecordRequest) {
        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(bookCopiesRentalState.getPatron()).isEqualTo(rentalRecordRequest.getPatron());
            softly.assertThat(bookCopiesRentalState.getStatus()).isEqualTo(rentalRecordRequest.getStatus());
        }
    }
}
