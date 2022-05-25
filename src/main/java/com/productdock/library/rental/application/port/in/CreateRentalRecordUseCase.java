package com.productdock.library.rental.application.port.in;


import com.productdock.library.rental.domain.RentalRequestDto;

public interface CreateRentalRecordUseCase {

    void create(RentalRequestDto rentalRequestDto, String userEmail);
}
