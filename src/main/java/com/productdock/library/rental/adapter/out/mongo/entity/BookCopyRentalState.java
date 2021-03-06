package com.productdock.library.rental.adapter.out.mongo.entity;

import com.productdock.library.rental.domain.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCopyRentalState implements Serializable {

    private String userEmail;
    private Date date;
    private RentalStatus status;
}
