package com.productdock.library.rental.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookRentalRecords {

    private final String bookId;
    private List<BookCopyRentalRecord> bookCopies;

    public BookRentalRecords(String bookId) {
        this.bookId = bookId;
        this.bookCopies = new ArrayList<>();
    }

    public BookRentalRecords(String bookId, List<BookCopyRentalRecord> records) {
        this.bookId = bookId;
        this.bookCopies = records;
    }

    public void applyAction(BookRentalAction rentalAction) {
        Optional<BookCopyRentalRecord> currentBookRecord = this.findByOwner(rentalAction.getInitiator());
        Optional<BookCopyRentalRecord> updatedBookRecord = rentalAction.performWithRespectTo(currentBookRecord);
        remove(currentBookRecord);
        add(updatedBookRecord);
    }

    private void remove(Optional<BookCopyRentalRecord> bookRecord) {
        if (bookRecord.isEmpty()) {
            return;
        }
        bookCopies.remove(bookRecord.get());
    }

    private void add(Optional<BookCopyRentalRecord> bookRecord) {
        if (bookRecord.isEmpty()) {
            return;
        }
        bookCopies.add(bookRecord.get());
    }

    private Optional<BookCopyRentalRecord> findByOwner(String owner) {
        return bookCopies.stream()
                .filter(book -> book.getUserEmail().equals(owner))
                .findFirst();
    }

    public List<BookCopyRentalRecord> getRents() {
        return bookCopies.stream()
                .filter(bookCopyRentalRecord -> bookCopyRentalRecord.isRent())
                .collect(Collectors.toList());
    }

    public List<BookCopyRentalRecord> getReservations() {
        return bookCopies.stream()
                .filter(bookCopyRentalRecord -> bookCopyRentalRecord.isReservation())
                .collect(Collectors.toList());
    }

}
