package com.productdock.library.rental.service;

import com.productdock.library.rental.domain.BookRentalRecord;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.productdock.library.rental.domain.UserActivityFactory.createUserActivity;

@Service
public record RentalRecordService(RentalRecordRepository rentalRecordRepository,
                                  BookRecordMapper bookRecordMapper,
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

    public Collection<BookRecordDto> getByBookId(String bookId) {
        Optional<RentalRecordEntity> recordEntity = rentalRecordRepository.findById(bookId);
        if(recordEntity.isEmpty()){
            return new ArrayList<>();
        }
        return bookRecordMapper.toDtoCollection(recordEntity.get().getInteractions());
    }
}
