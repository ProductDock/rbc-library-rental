package com.productdock.library.rental.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        assertThat(bookRentalRecord.getBookCopies().get(0).getPatron()).isEqualTo(rentalRecordEntity.getInteractions().get(0).getUserEmail());
        assertThat(bookRentalRecord.getBookCopies().get(0).getDate()).isEqualTo(rentalRecordEntity.getInteractions().get(0).getDate());
        assertThat(bookRentalRecord.getBookCopies().get(0).getStatus()).isEqualTo(rentalRecordEntity.getInteractions().get(0).getStatus());
    }
}
