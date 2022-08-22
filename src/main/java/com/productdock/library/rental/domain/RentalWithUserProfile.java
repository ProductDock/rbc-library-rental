package com.productdock.library.rental.domain;

import lombok.Builder;

import java.util.Date;

@Builder
public class RentalWithUserProfile {
    public final UserProfile user;
    public final RentalStatus status;
    public final Date date;
}
