package com.productdock.library.rental.adapter.in.web;

import com.productdock.library.rental.application.port.in.ExecuteRentalActionUseCase;
import com.productdock.library.rental.domain.RentalAction;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/rental/book/{bookId}/action")
public record ExecuteRentalActionApi(ExecuteRentalActionUseCase executeRentalActionUseCase) {

    @PostMapping
    @SneakyThrows
    public void executeAction(@PathVariable("bookId") String bookId, @RequestBody RentalActionRequest rentalActionRequest, Authentication authentication) {
        log.debug("POST request received - api/rental/book/{}/action, Payload: {}", bookId, rentalActionRequest);

        RentalAction rentalAction = RentalAction.builder()
                .bookId(bookId)
                .userId(((Jwt) authentication.getCredentials()).getClaim("email"))
                .action(rentalActionRequest.rentalAction)
                .build();
        executeRentalActionUseCase.executeAction(rentalAction);
    }

}
