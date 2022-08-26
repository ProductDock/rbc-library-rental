package com.productdock.library.rental.domain;

import com.productdock.library.rental.domain.activity.UserRentalActivity;
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
public class BookRentals {

    private String bookId;
    private List<BookCopyRentalState> bookCopiesRentalState;

    public BookRentals(String bookId) {
        this.bookId = bookId;
        bookCopiesRentalState = new ArrayList<>();
    }

    public void trackRentalActivity(UserRentalActivity activity) {
        log.debug("Add new rental request to book's rental record");
        Optional<BookCopyRentalState> previousRecord = findByPatron(activity.getInitiator());
        log.debug("Old record that is getting replaced : {}", previousRecord);
        Optional<BookCopyRentalState> newRecord = activity.changeStatusFrom(previousRecord);
        log.debug("New record : {}", newRecord);
        remove(previousRecord);
        add(newRecord);
    }

    public Collection<BookCopyRentalState> findExpiredReservations(ReservationExpirationPolicy policy) {
        return findReservations().stream().filter(res -> policy.isReservationExpired(res.date)).toList();
    }

    public void removeExpiredReservations(ReservationExpirationPolicy policy) {
        for (var bookCopy : findExpiredReservations(policy)) {
            log.debug("Removing expired book reservation for user with id: {} for book with id: {}", bookId, bookCopy.patron);
            remove(Optional.of(bookCopy));
        }
    }

    private void add(Optional<BookCopyRentalState> newRecord) {
        if (newRecord.isEmpty()) {
            return;
        }
        bookCopiesRentalState.add(newRecord.get());
    }

    private void remove(Optional<BookCopyRentalState> previousRecord) {
        if (previousRecord.isEmpty()) {
            return;
        }
        bookCopiesRentalState.remove(previousRecord.get());
    }

    private Collection<BookCopyRentalState> findReservations() {
        return this.bookCopiesRentalState.stream().filter(i -> i.getStatus().equals(RentalStatus.RESERVED)).toList();
    }

    private Optional<BookCopyRentalState> findByPatron(String initiator) {
        return bookCopiesRentalState.stream().filter(book -> book.getPatron().equals(initiator)).findFirst();
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class BookCopyRentalState {

        private Date date;
        private String patron;
        private RentalStatus status;

        public BookCopyRentalState() {
        }

        public BookCopyRentalState(String patron, RentalStatus status) {
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
