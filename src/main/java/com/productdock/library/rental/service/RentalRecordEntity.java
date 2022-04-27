package com.productdock.library.rental.service;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.exception.NotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Document("rental-record")
@Builder
public class RentalRecordEntity implements Serializable {

    @Id
    private String bookId;
    private List<BookInteraction> reservations;
    private List<BookInteraction> rents;

    public RentalRecordEntity(String bookId, List<BookInteraction> reservations, List<BookInteraction> rents) {
        this.bookId = bookId;
        this.reservations = new ArrayList<BookInteraction>();
        this.rents = new ArrayList<BookInteraction>();
    }

    public RentalRecordEntity() {
        this.reservations = new ArrayList<BookInteraction>();
        this.rents = new ArrayList<BookInteraction>();
    }

}
