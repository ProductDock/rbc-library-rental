package com.productdock.library.rental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        Optional<BookCopy> previousRecord = findByPatron(activity.getInitiator());
        Optional<BookCopy> newRecord = activity.changeStatusFrom(previousRecord);
        remove(previousRecord);
        add(newRecord);
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
