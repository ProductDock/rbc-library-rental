package com.productdock.library.rental.domain;

import com.productdock.library.rental.service.RentalStatus;

public class UserActivityFactory {

    public static UserBookActivity createUserActivity(RentalStatus bookStatus, String userEmail) {
        switch (bookStatus) {
            case RENTED -> {
                return new UserBorrowsABookActivity(userEmail);
            }
            case RESERVED -> {
                return new UserReservesABookActivity(userEmail);
            }
            case RETURNED -> {
                return new UserReturnsABookActivity(userEmail);
            }
            default -> {
                return null;
            }
        }
    }
}
