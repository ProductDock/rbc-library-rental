package com.productdock.library.rental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@DataStructure
public class RentalAction {

    public final String bookId;
    public final String userId;
    public final RentalActionType action;

}
