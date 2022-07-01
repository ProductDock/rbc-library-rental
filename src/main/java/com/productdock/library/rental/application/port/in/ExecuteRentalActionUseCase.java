package com.productdock.library.rental.application.port.in;

import com.productdock.library.rental.domain.RentalAction;

public interface ExecuteRentalActionUseCase {

    void executeAction(RentalAction rentalAction);
}
