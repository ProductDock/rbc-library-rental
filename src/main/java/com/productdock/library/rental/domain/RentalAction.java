package com.productdock.library.rental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class RentalAction {

    public String bookId;
    public String userId;
    public RentalActionType action;
}
