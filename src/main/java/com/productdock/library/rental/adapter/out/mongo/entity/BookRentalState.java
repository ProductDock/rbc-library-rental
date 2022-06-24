package com.productdock.library.rental.adapter.out.mongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;


@Data
@Document("rental-record")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRentalState implements Serializable {

    @Id
    private String id;
    private String bookId;
    @Singular("bookCopyRentalState")
    private List<BookCopyRentalState> bookCopiesRentalState;

}
