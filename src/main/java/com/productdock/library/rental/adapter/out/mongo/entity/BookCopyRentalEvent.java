package com.productdock.library.rental.adapter.out.mongo.entity;

import com.productdock.library.rental.domain.RentalActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("rental-event")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyRentalEvent {

    @Id
    private String id;
    private String bookId;
    private String userId;
    private RentalActionType action;
    private Date date;
}
