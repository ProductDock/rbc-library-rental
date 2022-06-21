package com.productdock.library.rental.service;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.domain.BookRentalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.productdock.library.rental.data.provider.BookRentalRecordMother.bookRentalRecordWithRentRequest;
import static com.productdock.library.rental.data.provider.RentalRecordEntityMother.defaultRentalRecordEntity;
import static com.productdock.library.rental.data.provider.RentalRecordEntityMother.defaultRentalRecordEntityBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BookRentalRecordMapperImpl.class, BookCopyMapperImpl.class})
class BookRentalRecordMapperShould {

    @Autowired
    private BookRentalRecordMapper bookRentalRecordMapper;

    @Autowired
    private BookCopyMapper bookCopyMapper;

    @Test
    void mapRentalRecordEntityCollectionToBookRentalRecordCollection() {
        var firstRentalRecordEntity = defaultRentalRecordEntity();
        var secondRentalRecordEntity = defaultRentalRecordEntityBuilder().bookId("2").build();
        var rentalRecordCollection = List.of(firstRentalRecordEntity, secondRentalRecordEntity);

        var bookRentalRecordCollection = bookRentalRecordMapper.toDomainCollection(rentalRecordCollection).stream().toList();

        assertThat(bookRentalRecordCollection.get(0).getBookId()).isEqualTo(firstRentalRecordEntity.getBookId());
        assertThatRecordsAreMatching(bookRentalRecordCollection.get(0).getBookCopies(), firstRentalRecordEntity.getInteractions());
        assertThat(bookRentalRecordCollection.get(1).getBookId()).isEqualTo(secondRentalRecordEntity.getBookId());
        assertThatRecordsAreMatching(bookRentalRecordCollection.get(1).getBookCopies(), secondRentalRecordEntity.getInteractions());
    }

    @Test
    void mapRentalRecordEntityToBookRentalRecord() {
        var rentalRecordEntity = defaultRentalRecordEntity();

        var bookRentalRecord = bookRentalRecordMapper.toDomain(rentalRecordEntity);

        assertThat(bookRentalRecord.getBookId()).isEqualTo(rentalRecordEntity.getBookId());
        assertThatRecordsAreMatching(bookRentalRecord.getBookCopies(), rentalRecordEntity.getInteractions());
    }

    @Test
    void mapBookRentalRecordToRentalRecordEntity() {
        var bookRentalRecord = bookRentalRecordWithRentRequest();

        var rentalRecordEntity = bookRentalRecordMapper.toEntity(bookRentalRecord);

        assertThat(bookRentalRecord.getBookId()).isEqualTo(rentalRecordEntity.getBookId());
        assertThatRecordsAreMatching(bookRentalRecord.getBookCopies(), rentalRecordEntity.getInteractions());
    }

    private void assertThatRecordsAreMatching(List<BookRentalRecord.BookCopy> bookCopies, List<BookInteraction> bookInteractions) {
        assertThat(bookCopies).hasSameSizeAs(bookInteractions);
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
