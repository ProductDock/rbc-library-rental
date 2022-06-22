package com.productdock.library.rental.service;

import com.productdock.library.rental.domain.BookRentalRecord;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.productdock.library.rental.domain.UserActivityFactory.createUserActivity;

@Slf4j
@Service
@AllArgsConstructor
public class RentalRecordService {

    private RentalRecordRepository rentalRecordRepository;
    private BookRecordMapper bookRecordMapper;
    private BookRentalRecordMapper bookRentalRecordMapper;
    private RentalRecordPublisher rentalRecordPublisher;


    @SneakyThrows
    public void create(RentalRequestDto rentalRequestDto, String userEmail) {
        log.debug("Create rental record for book {} with action {} ", rentalRequestDto.bookId, rentalRequestDto.requestedStatus);
        var bookRentalRecord = createBookRentalRecord(rentalRequestDto.bookId);

        var activity = createUserActivity(rentalRequestDto.requestedStatus, userEmail);
        bookRentalRecord.trackActivity(activity);
        saveRentalRecord(bookRentalRecord);

        rentalRecordPublisher.sendMessage(bookRentalRecord);
    }

    public Collection<BookRentalRecord> findWithReservations() {
        var reservedRentalRecords = rentalRecordRepository.findWithReservations();
        return bookRentalRecordMapper.toDomainCollection(reservedRentalRecords);
    }

    private BookRentalRecord createBookRentalRecord(String bookId) {
        log.debug("Find book's rental record in database by id: {}", bookId);
        var recordEntity = rentalRecordRepository.findByBookId(bookId);
        if (recordEntity.isEmpty()) {
            log.debug("Create book's rental record in database for id: {}", bookId);
            return new BookRentalRecord(bookId);
        } else {
            return bookRentalRecordMapper.toDomain(recordEntity.get());
        }
    }

    public void saveRentalRecord(BookRentalRecord bookRentalRecord) {
        log.debug("Save new book's rental record in database with id : {}", bookRentalRecord);
        var previousRecordEntity = rentalRecordRepository.findByBookId(bookRentalRecord.getBookId());
        var newRecordEntity = bookRentalRecordMapper.toEntity(bookRentalRecord);
        if (previousRecordEntity.isPresent()) {
            newRecordEntity.setId(previousRecordEntity.get().getId());
        }
        rentalRecordRepository.save(newRecordEntity);
    }

    public Collection<BookRecordDto> getByBookId(String bookId) {
        log.debug("Get rental records for the {} book", bookId);
        Optional<RentalRecordEntity> recordEntity = rentalRecordRepository.findByBookId(bookId);
        if (recordEntity.isEmpty()) {
            return new ArrayList<>();
        }
        return bookRecordMapper.toDtoCollection(recordEntity.get().getInteractions());
    }
}
