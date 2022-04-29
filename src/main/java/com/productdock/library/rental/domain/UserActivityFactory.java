package com.productdock.library.rental.domain;

import com.productdock.library.rental.service.RentalStatus;

public class UserActivityFactory {

    private UserActivityFactory() {
    }

    public static UserBookActivity createUserActivity(RentalStatus bookStatus, String userEmail) {
        return switch (bookStatus) {
            case RENTED -> new UserBorrowsABookActivity(userEmail);
            case RESERVED -> new UserReservesABookActivity(userEmail);
            case RETURNED -> new UserReturnsABookActivity(userEmail);
            default -> null;
        };
    }
}
