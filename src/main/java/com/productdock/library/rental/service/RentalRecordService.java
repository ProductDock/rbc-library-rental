package com.productdock.library.rental.service;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.exception.BookRentalException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.productdock.library.rental.domain.UserActivityFactory.createUserActivity;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalRecordService {

    @NonNull
    private RentalRecordRepository rentalRecordRepository;
    @NonNull
    private BookRecordMapper bookRecordMapper;
    @NonNull
    private BookRentalRecordMapper bookRentalRecordMapper;
    @NonNull
    private RentalRecordPublisher rentalRecordPublisher;

    @Value("${scheduled.task.delay}")
    private Integer DELAY;

    @SneakyThrows
    public void create(RentalRequestDto rentalRequestDto, String userEmail) {
        log.debug("Create rental record for book {} with action {} ", rentalRequestDto.bookId, rentalRequestDto.requestedStatus);
        var bookRentalRecord = createBookRentalRecord(rentalRequestDto.bookId);

        var activity = createUserActivity(rentalRequestDto.requestedStatus, userEmail);
        if (rentalRequestDto.requestedStatus.equals(RentalStatus.RESERVED)) {
            scheduleCancelReservation(rentalRequestDto, userEmail);
        }

        bookRentalRecord.trackActivity(activity);

        saveRentalRecord(bookRentalRecord);

        rentalRecordPublisher.sendMessage(bookRentalRecord);
    }

    private void scheduleCancelReservation(RentalRequestDto rentalRequestDto, String userEmail) {
        log.debug("Scheduling cancel reservation task for book {} for user {} at: {} time", rentalRequestDto.bookId, userEmail, new Date());
        rentalRequestDto.requestedStatus = RentalStatus.CANCELED;
        Runnable task = () -> createCancelReservationTask(rentalRequestDto, userEmail);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        executor.schedule(task, DELAY, TimeUnit.SECONDS);
    }

    private void createCancelReservationTask(RentalRequestDto rentalRequestDto, String userEmail) {
        log.debug("Executing cancel reservation task for book {} for user {} at: {} time", rentalRequestDto.bookId, userEmail, new Date());
        var usersInteraction = findUsersBookInteraction(userEmail, rentalRequestDto.bookId);

        if (usersInteraction.isEmpty() || beforeDelay(usersInteraction.get().getDate())) {
            return;
        }

        create(rentalRequestDto, userEmail);
    }

    private boolean beforeDelay(Date interactionDate) {
        return interactionDate.getTime() + TimeUnit.SECONDS.toMillis(DELAY) > new Date().getTime();
    }

    private Optional<BookInteraction> findUsersBookInteraction(String userEmail, String bookId) {
        log.debug("Find users book interaction for book with id: {} and user with id: {}", bookId, userEmail);
        var rentalRecords = rentalRecordRepository.findByBookId(bookId);
        if (rentalRecords.isEmpty()) {
            throw new BookRentalException("Book rental record not found");
        }

        return rentalRecords.get().getInteractions().stream()
                .filter(i -> i.getUserEmail().equals(userEmail)).findFirst();
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

    private void saveRentalRecord(BookRentalRecord bookRentalRecord) {
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
