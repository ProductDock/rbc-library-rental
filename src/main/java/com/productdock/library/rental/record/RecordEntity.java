package com.productdock.library.rental.record;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.exception.NotFoundException;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Document("rental-record")
@Builder
public class RecordEntity implements Serializable {

    @Id
    private String bookId;
    private List<BookInteraction> reservations;
    private List<BookInteraction> rents;

    public RecordEntity(String bookId, List<BookInteraction> reservations, List<BookInteraction> rents) {
        this.bookId = bookId;
        this.reservations = new ArrayList<BookInteraction>();
        this.rents = new ArrayList<BookInteraction>();
    }

    public RecordEntity() {
        this.reservations = new ArrayList<BookInteraction>();
        this.rents = new ArrayList<BookInteraction>();
    }

    public void rentBook(String email) {
        isBookRentedByUser(email);
        cancelReservation(email);
        addRent(new BookInteraction(email, new Date()));
    }

    public void reserveBook(String email) {
        isBookRentedByUser(email);
        isBookReservedByUser(email);
        addReservation(new BookInteraction(email, new Date()));
    }

    public void returnBook(String email) {
        cancelRent(email);
    }

    private void cancelReservation(String email) {
        reservations.removeIf(res -> res.getUserEmail().equals(email));
    }

    private void cancelRent(String email) {
        rents.removeIf(res -> res.getUserEmail().equals(email));
    }

    private void addReservation(BookInteraction bookInteraction) {
        reservations.add(bookInteraction);
    }

    private void addRent(BookInteraction bookInteraction) {
        rents.add(bookInteraction);
    }

    private void isBookRentedByUser(String email) {
        for(BookInteraction b : rents) {
            if(b.getUserEmail().equals(email)) {
                throw new NotFoundException();
            }
        }
    }

    private void isBookReservedByUser(String email) {
        for(BookInteraction b : reservations) {
            if(b.getUserEmail().equals(email)) {
                throw new NotFoundException();
            }
        }
    }
}
