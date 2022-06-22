package com.productdock.library.rental.domain;

import com.productdock.library.rental.scheduled.ReservationExpirationPolicy;
import com.productdock.library.rental.service.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BookRentalRecord {

    private String bookId;
    private List<BookCopy> bookCopies;

    public BookRentalRecord(String bookId) {
        this.bookId = bookId;
        bookCopies = new ArrayList<>();
    }

    public void trackActivity(UserBookActivity activity) {
        log.debug("Add new rental request to book's rental record");
        Optional<BookCopy> previousRecord = findByPatron(activity.getInitiator());
        log.debug("Old record that is getting replaced : {}", previousRecord);
        Optional<BookCopy> newRecord = activity.changeStatusFrom(previousRecord);
        log.debug("New record : {}", newRecord);
        remove(previousRecord);
        add(newRecord);
    }

    public void removeExpiredReservations(ReservationExpirationPolicy policy) {
        for (var bookCopy : findReservedInteractions()) {
            if (policy.isExpired(bookCopy.date)) {
                log.debug("Removing expired book reservation for user with id: {} for book with id: {}", bookId, bookCopy.patron);
                remove(Optional.of(bookCopy));
            }
        }
    }

    private void add(Optional<BookCopy> newRecord) {
        if (newRecord.isEmpty()) {
            return;
        }
        bookCopies.add(newRecord.get());
    }

    private void remove(Optional<BookCopy> previousRecord) {
        if (previousRecord.isEmpty()) {
            return;
        }
        bookCopies.remove(previousRecord.get());
    }

    private Collection<BookCopy> findReservedInteractions() {
        return this.bookCopies.stream().filter(i -> i.getStatus().equals(RentalStatus.RESERVED)).toList();
    }

    private Optional<BookCopy> findByPatron(String initiator) {
        return bookCopies.stream().filter(book -> book.getPatron().equals(initiator)).findFirst();
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class BookCopy {

        private Date date;
        private String patron;
        private RentalStatus status;

        public BookCopy() {
        }

        public BookCopy(String patron, RentalStatus status) {
            this.patron = patron;
            this.status = status;
            this.date = new Date();
        }

        public boolean isReservation() {
            return status.equals(RentalStatus.RESERVED);
        }

        public boolean isBorrow() {
            return status.equals(RentalStatus.RENTED);
        }
    }
}
