package com.productdock.library.rental.domain;

public class RentABookAction extends BookAction {

    private final String email;

    public RentABookAction(String userEmail) {
        this.email = userEmail;
    }

}
