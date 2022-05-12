package com.productdock.library.rental.service;

import com.productdock.library.rental.domain.BookRentalRecord;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.productdock.library.rental.domain.UserActivityFactory.createUserActivity;

@Service
public record RentalRecordService(RentalRecordRepository rentalRecordRepository,
                                  BookRentalRecordMapper bookRentalRecordMapper,
                                  RentalRecordPublisher rentalRecordPublisher) {

    @SneakyThrows
    public void create(RentalRequestDto rentalRequestDto, String userEmail) {
        var bookRentalRecord = createBookRentalRecord(rentalRequestDto.bookId);

        var activity = createUserActivity(rentalRequestDto.requestedStatus, userEmail);
        bookRentalRecord.trackActivity(activity);

        saveRentalRecord(bookRentalRecord);

        rentalRecordPublisher.sendMessage(bookRentalRecord);
    }

    private BookRentalRecord createBookRentalRecord(String bookId) {
        var recordEntity = rentalRecordRepository.findByBookId(bookId);
        if (recordEntity.isEmpty()) {
            return new BookRentalRecord(bookId);
        } else {
            return bookRentalRecordMapper.toDomain(recordEntity.get());
        }
    }

    private void saveRentalRecord(BookRentalRecord bookRentalRecord) {
        var previousRecordEntity = rentalRecordRepository.findByBookId(bookRentalRecord.getBookId());
        var newRecordEntity = bookRentalRecordMapper.toEntity(bookRentalRecord);
        if (previousRecordEntity.isPresent()) {
            newRecordEntity.setId(previousRecordEntity.get().getId());
        }
        rentalRecordRepository.save(newRecordEntity);
    }
}
