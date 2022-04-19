package com.productdock.library.rental.record;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Document("rental-record")
@NoArgsConstructor
public class RecordEntity implements Serializable {

    private final static long serialVersionUID = 22222L;

    @Id
    private String bookId;
    private List<BookInteraction> reservations = new ArrayList<BookInteraction>();
    private List<BookInteraction> rents = new ArrayList<BookInteraction>();

    public void rentBook(String email) {
        this.isBookRentedByUser(email);
        this.cancelReservation(email);
        this.addRent(new BookInteraction(email, new Date()));
    }

    public void reserveBook(String email) {
        this.isBookRentedByUser(email);
        this.isBookReservedByUser(email);
        this.addReservation(new BookInteraction(email, new Date()));
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
        System.out.println("Book reserved!");
    }

    private void addRent(BookInteraction bookInteraction) {
        rents.add(bookInteraction);
        System.out.println("Book rented!");
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
