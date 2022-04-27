package com.productdock.library.rental.domain;

import com.productdock.library.rental.record.RentalStatus;

public class ActivityFactory {

    public static UserBookActivity create(RentalStatus bookStatus, String userEmail) {
        switch (bookStatus) {
            case RENT -> {
                return new UserBorrowsABookActivity(userEmail);
            }
            case RESERVE -> {
                return new UserReservesABookActivity(userEmail);
            }
            case RETURN -> {
                return new UserReturnsABookActivity(userEmail);
            }
            default -> {
                return null;
            }
        }
    }
}
