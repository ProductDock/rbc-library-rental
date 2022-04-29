package com.productdock.library.rental.service;

import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.kafka.RentalsPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.productdock.library.rental.domain.UserActivityFactory.createUserActivity;

@Service
public record RentalRecordService(RentalRecordMapper recordMapper, RentalsPublisher publisher,
                                  RentalRecordRepository rentalRecordRepository,
                                  BookRentalRecordMapper bookRentalRecordMapper,
                                  BookCopyMapper bookInteractionMapper,
                                  RentalRecordsMessageMapper rentalRecordsMessageMapper) {

    public void create(RentalRequestDto rentalRequestDto, String userEmail) throws Exception {
        var bookRentalRecord = createBookRentalRecord(rentalRequestDto.bookId);

        var activity = createUserActivity(rentalRequestDto.requestedStatus, userEmail);
        bookRentalRecord.trackActivity(activity);

        saveRentalRecord(bookRentalRecord);

        var rentalRecordsMessage = rentalRecordsMessageMapper.toMessage(bookRentalRecord);
        publisher.sendMessage(rentalRecordsMessage);
    }

    private BookRentalRecord createBookRentalRecord(String bookId) {
        Optional<RentalRecordEntity> recordEntity = rentalRecordRepository.findById(bookId);
        if (recordEntity.isEmpty()) {
            return new BookRentalRecord(bookId);
        } else {
            return bookRentalRecordMapper.toDomain(recordEntity.get());
        }
    }

    private void saveRentalRecord(BookRentalRecord bookRentalRecord) {
        RentalRecordEntity entity = bookRentalRecordMapper.toEntity(bookRentalRecord);
        rentalRecordRepository.save(entity);
    }
}
