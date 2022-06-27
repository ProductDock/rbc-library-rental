package com.productdock.library.rental.application.port.in;

import com.productdock.library.rental.domain.ds.RentalAction;

public interface ExecuteRentalActionUseCase {

    void executeAction(RentalAction rentalAction);
}
