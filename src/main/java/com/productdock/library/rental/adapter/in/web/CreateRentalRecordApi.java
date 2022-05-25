package com.productdock.library.rental.adapter.in.web;

import com.productdock.library.rental.application.port.in.CreateRentalRecordUseCase;
import com.productdock.library.rental.domain.RentalRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/rental/record")
class CreateRentalRecordApi {

    private final CreateRentalRecordUseCase createRentalRecordUseCase;

    @PostMapping
    @SneakyThrows
    public void createRecord(@RequestBody RentalRequestDto rentalRequestDto, Authentication authentication) {
        createRentalRecordUseCase.create(rentalRequestDto, ((Jwt) authentication.getCredentials()).getClaim("email"));
    }
}
