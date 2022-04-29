package com.productdock.library.rental.service;

import com.productdock.library.rental.book.BookInteraction;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;


@Data
@Document("rental-record")
@Builder
@NoArgsConstructor
public class RentalRecordEntity implements Serializable {

    @Id
    private String bookId;
    private List<BookInteraction> interactions;

    public RentalRecordEntity(String bookId, List<BookInteraction> interactions) {
        this.bookId = bookId;
        this.interactions = interactions;
    }

}