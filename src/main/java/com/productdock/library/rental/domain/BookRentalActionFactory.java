package com.productdock.library.rental.domain;

public class BookRentalActionFactory {

    public static BookRentalAction create(String bookStatus, String userEmail) {
        if (bookStatus.equals("RENT")) {
            return new RentABookAction(userEmail);
        } else if (bookStatus.equals("RESERVE")) {
            return new ReserveABookAction(userEmail);
        } else if (bookStatus.equals("RETURN")) {
            return new ReturnABookAction(userEmail);
        } else {
            return null;
        }
    }

}
