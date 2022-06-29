package com.productdock.library.rental.domain.ds;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class RentalAction {

    public final String bookId;
    public final String userId;
    public final RentalActionType action;

}
