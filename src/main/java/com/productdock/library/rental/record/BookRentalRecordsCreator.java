package com.productdock.library.rental.record;

import com.productdock.library.rental.domain.BookCopyRentalRecord;
import com.productdock.library.rental.domain.BookRentalRecords;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Stream.concat;

@Component
public class BookRentalRecordsCreator {

    public BookRentalRecords makeFrom(RecordDto rentalRecordDto, Optional<RecordEntity> recordEntity) {
        BookRentalRecords domain;
        if (recordEntity.isEmpty()) {
            domain = new BookRentalRecords(rentalRecordDto.bookId);
        } else {
            List<BookCopyRentalRecord> records =
                    concat(
                            recordEntity.get().getRents().stream()
                                    .map(r -> new BookCopyRentalRecord(RentalAction.RENT, r.getUserEmail(), r.getDate())),
                            recordEntity.get().getReservations().stream()
                                    .map(r -> new BookCopyRentalRecord(RentalAction.RESERVE, r.getUserEmail(), r.getDate())))
                            .collect(Collectors.toList());
            domain = new BookRentalRecords(rentalRecordDto.bookId, records);
        }
        return domain;
    }
}
