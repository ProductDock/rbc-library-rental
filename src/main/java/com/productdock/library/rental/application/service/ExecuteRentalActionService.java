package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.in.ExecuteRentalActionUseCase;
import com.productdock.library.rental.application.port.out.messaging.RentalRecordMessagingOutPort;
import com.productdock.library.rental.application.port.out.persistence.RentalRecordPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.domain.activity.UserActivityFactory;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ExecuteRentalActionService implements ExecuteRentalActionUseCase {

    private RentalRecordPersistenceOutPort rentalRecordRepository;
    private RentalRecordMessagingOutPort rentalRecordPublisher;

    @Override
    @SneakyThrows
    public void executeAction(RentalAction rentalAction) {
        log.debug("User {} executes action {} for book {}  ", rentalAction.userId, rentalAction.action, rentalAction.bookId);
        var bookRentalRecord = createBookRentalRecord(rentalAction.bookId);

        trackUserActivityInRecord(rentalAction, bookRentalRecord);

        rentalRecordRepository.save(bookRentalRecord);
        rentalRecordPublisher.sendMessage(bookRentalRecord);
    }

    private BookRentalRecord createBookRentalRecord(String bookId) {
        log.debug("Find book's rental record in database by id: {}", bookId);
        var record = rentalRecordRepository.findByBookId(bookId);
        if (record.isEmpty()) {
            log.debug("Create book's rental record in database for id: {}", bookId);
            return new BookRentalRecord(bookId);
        }
        return record.get();
    }

    private void trackUserActivityInRecord(RentalAction rentalAction, BookRentalRecord bookRentalRecord) {
        var activity = UserActivityFactory.createUserActivity(rentalAction.action, rentalAction.userId);
        bookRentalRecord.trackActivity(activity);
    }

}
