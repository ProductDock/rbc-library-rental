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

    public void create(RentalRequest rentalRequest, String userEmail) throws Exception {
        var bookRentalRecord = createBookRentalRecord(rentalRequest.bookId);

        var activity = createUserActivity(rentalRequest.requestedStatus, userEmail);
        bookRentalRecord.trackActivity(activity);

        saveRentalRecord(bookRentalRecord);

        var rentalRecordsMessage = rentalRecordsMessageMapper.toMessage(bookRentalRecord);
        publisher.sendMessage(rentalRecordsMessage);
    }

    public void processFailedRequest(FailedRequest failedRequest) {
        var bookRentalRecord = createBookRentalRecord(failedRequest.getBookId());
        bookRentalRecord.undoBadRequest(failedRequest);
        rentalRecordRepository.save(bookRentalRecordMapper.toEntity(bookRentalRecord));
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
