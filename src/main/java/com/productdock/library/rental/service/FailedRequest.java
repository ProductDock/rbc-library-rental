package com.productdock.library.rental.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailedRequest {

    private String bookId;
    private String patron;
    private RentalStatus requestStatus;
}
