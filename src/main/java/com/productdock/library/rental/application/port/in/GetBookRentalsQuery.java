package com.productdock.library.rental.application.port.in;

import com.productdock.library.rental.domain.RentalWithUserProfile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public interface GetBookRentalsQuery {

    Collection<RentalWithUserProfile> getBookCopiesRentalState(String bookId) throws URISyntaxException, IOException, InterruptedException;
}
