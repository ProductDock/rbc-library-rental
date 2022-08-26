package com.productdock.library.rental.application.port.out.persistence;

import com.productdock.library.rental.domain.BookRentalEvent;

public interface BookRentalEventPersistenceOutPort {

    void save(BookRentalEvent bookRentalEvent);
}
