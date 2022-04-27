package com.productdock.library.rental.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


@NoArgsConstructor
@AllArgsConstructor
public class RentalRecordDto {

    @NonNull
    public String bookId;
    public RentalStatus bookStatus;
}
