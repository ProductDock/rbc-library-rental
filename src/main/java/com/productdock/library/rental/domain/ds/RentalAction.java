package com.productdock.library.rental.domain.ds;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class RentalAction {

    public String bookId;
    public String userId;
    public RentalActionType action;
}
