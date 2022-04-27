package com.productdock.library.rental.domain;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.service.RentalStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public String getBookId() {
        return bookId;
    }

    public List<BookCopy> getReservations() {
        return bookCopies.stream().filter(bookCopy -> bookCopy.isReservation()).collect(Collectors.toList());
    }

    public List<BookCopy> getBorrows() {
        return bookCopies.stream().filter(bookCopy -> bookCopy.isBorrow()).collect(Collectors.toList());
    }

    public static class BookCopy {

        private final Date date;
        private final String patron;
        private final RentalStatus status;

        public BookCopy(String patron, RentalStatus status) {
            this.patron = patron;
            this.status = status;
            this.date = new Date();
        }

        public BookCopy(Date date, String patron, RentalStatus status) {
            this.date = date;
            this.patron = patron;
            this.status = status;
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
