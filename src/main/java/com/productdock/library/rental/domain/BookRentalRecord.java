package com.productdock.library.rental.domain;

import com.productdock.library.rental.record.RentalStatus;

import java.util.List;
import java.util.Optional;

public class BookRentalRecord {
    private final String bookId;
    private List<BookCopy> bookCopies;

    public BookRentalRecord(String bookId) {
        this.bookId = bookId;
    }

    public BookRentalRecord(String bookId, List<BookCopy> bookCopies) {
        this.bookId = bookId;
        this.bookCopies = bookCopies;
    }

    public void trackActivity(UserBookActivity activity) {
        Optional<BookCopy> previousRecord = this.findByPatron(activity.getInitiator());
        Optional<BookCopy> newRecord =  activity.executeWithRespectTo(previousRecord);
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

    static class BookCopy {
        private String patron;
        private RentalStatus status;

        public BookCopy(String initiator, RentalStatus rent) {
        }

        public String getPatron() {
            return patron;
        }

        public boolean isReservation() {
            return status.equals(RentalStatus.RESERVE);
        }

        public boolean isBorrow() {
            return status.equals(RentalStatus.RENT);
        }
    }
}
