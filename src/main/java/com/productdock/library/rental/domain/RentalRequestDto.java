package com.productdock.library.rental.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestDto {

    public String bookId;
    public RentalStatus requestedStatus;
}
