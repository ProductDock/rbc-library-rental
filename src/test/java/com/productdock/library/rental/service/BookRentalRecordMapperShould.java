package com.productdock.library.rental.service;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.domain.BookRentalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.productdock.library.rental.data.provider.RentalRecordEntityMother.defaultRentalRecordEntity;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BookRentalRecordMapperImpl.class, BookCopyMapperImpl.class})
class BookRentalRecordMapperShould {

    @Autowired
    private BookRentalRecordMapper bookRentalRecordMapper;

    @Autowired
    private BookCopyMapper bookCopyMapper;

    @Test
    void mapRentalRecordEntityToBookRentalRecord() {
        var rentalRecordEntity = defaultRentalRecordEntity();

        var bookRentalRecord = bookRentalRecordMapper.toDomain(rentalRecordEntity);

        assertThat(bookRentalRecord.getBookId()).isEqualTo(rentalRecordEntity.getBookId());
        assertThatRecordsAreMatching(bookRentalRecord.getBookCopies(), rentalRecordEntity.getInteractions());
    }

    private void assertThatRecordsAreMatching (List<BookRentalRecord.BookCopy> bookCopies, List<BookInteraction> bookInteractions) {
        assertThat(bookCopies).hasSameSizeAs(bookInteractions.size());
        var bookCopy = bookCopies.get(0);
        var bookInteraction = bookInteractions.get(0);
        assertThatBookCopyIsMatching(bookCopy, bookInteraction);
    }

    private void assertThatBookCopyIsMatching(BookRentalRecord.BookCopy bookCopy, BookInteraction bookInteraction) {
        assertThat(bookCopy.getPatron()).isEqualTo(bookInteraction.getUserEmail());
        assertThat(bookCopy.getStatus()).isEqualTo(bookInteraction.getStatus());
        assertThat(bookCopy.getDate()).isEqualTo(bookInteraction.getDate());
    }
}
