package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.ds.RentalActionType;

public class UserRentalActivityFactory {

    private UserRentalActivityFactory() {
    }

    public static UserRentalActivity userRentalActivity(RentalActionType actionType, String userId) {
        return switch (actionType) {
            case RENT -> new UserBorrowsABookActivity(userId);
            case RESERVE -> new UserReservesABookActivity(userId);
            case RETURN -> new UserReturnsABookActivity(userId);
            case CANCEL_RESERVATION -> new UserCancelsABookReservationActivity(userId);
            default -> null;
        };
    }
}
