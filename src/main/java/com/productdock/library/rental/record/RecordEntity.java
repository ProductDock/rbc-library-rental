package com.productdock.library.rental.record;

import com.productdock.library.rental.producer.messages.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RecordEntity {
    private Long id;
    private Notification notification;
}
