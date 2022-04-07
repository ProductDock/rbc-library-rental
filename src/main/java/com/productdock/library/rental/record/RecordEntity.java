package com.productdock.library.rental.record;

import com.productdock.library.rental.producer.messages.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;


@Data
@Document("rental-record")
@NoArgsConstructor
public class RecordEntity {

    @Id
    private String id;
    private String bookId;
    private String bookStatus;
    private String userEmail;

    public RecordEntity(UUID bookId, String bookStatus, String userEmail){
        this.id = UUID.randomUUID().toString();
        this.bookId = bookId.toString();
        this.bookStatus = bookStatus;
        this.userEmail = userEmail;
    }
}
