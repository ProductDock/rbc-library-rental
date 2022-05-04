package com.productdock.library.rental.service;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.domain.BookRentalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.productdock.library.rental.data.provider.BookRentalRecordMother.defaultBookRentalRecordBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RentalRecordsMessageMapperImpl.class})

class RentalRecordsMessageMapperShould {

    @Autowired
    private RentalRecordsMessageMapper rentalRecordsMessageMapper;

    @Test
    void mapBookRentalRecordToRentalRecordsMessage() {
        var bookRentalRecord = defaultBookRentalRecordBuilder().build();

        var rentalRecordsMessage = rentalRecordsMessageMapper.toMessage(bookRentalRecord);

        assertThat(bookRentalRecord.getBookId()).isEqualTo(rentalRecordsMessage.getBookId());
        assertThatRecordsAreMatching(bookRentalRecord.getBookCopies(), rentalRecordsMessage.getRentalRecords());
    }

    private void assertThatRecordsAreMatching (List<BookRentalRecord.BookCopy> bookCopies, List<RentalRecordsMessage.RentalRecordRequest> rentalRecordRequests) {
        assertThat(bookCopies).hasSameSizeAs(rentalRecordRequests);
        var bookCopy = bookCopies.get(0);
        var rentalRecordRequest = rentalRecordRequests.get(0);
        assertThatBookCopyIsMatching(bookCopy, rentalRecordRequest);
    }

    private void assertThatBookCopyIsMatching(BookRentalRecord.BookCopy bookCopy, RentalRecordsMessage.RentalRecordRequest rentalRecordRequest) {
        assertThat(bookCopy.getPatron()).isEqualTo(rentalRecordRequest.getPatron());
        assertThat(bookCopy.getStatus()).isEqualTo(rentalRecordRequest.getStatus());
    }
}
