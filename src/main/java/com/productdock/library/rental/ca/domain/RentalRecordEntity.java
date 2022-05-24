package com.productdock.library.rental.ca.domain;

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
public class RentalRecordEntity implements Serializable {

    @Id
    private String id;
    private String bookId;
    @Singular
    private List<BookInteraction> interactions;

}
