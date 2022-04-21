package com.productdock.library.rental.domain;

public class BookActionFactory {

    public static BookAction create(String bookStatus, String userEmail) {
        if (bookStatus.equals("RENT")) {
            return new RentABookAction(userEmail);
        } else if (bookStatus.equals("RESERVE")) {
            //recordEntity.reserveBook(userEmail);
            return null;
        } else {
            //recordEntity.returnBook(userEmail);
            return null;
        }
    }

}
