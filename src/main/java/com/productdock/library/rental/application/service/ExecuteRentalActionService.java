package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.in.ExecuteRentalActionUseCase;
import com.productdock.library.rental.application.port.out.messaging.BookRentalsMessagingOutPort;
import com.productdock.library.rental.application.port.out.persistence.BookRentalEventPersistenceOutPort;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentalEvent;
import com.productdock.library.rental.domain.RentalAction;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.productdock.library.rental.domain.activity.UserRentalActivityFactory.userRentalActivity;

@Slf4j
@Service
@AllArgsConstructor
class ExecuteRentalActionService implements ExecuteRentalActionUseCase {

    private BookRentalsPersistenceOutPort bookRentalsRepository;
    private BookRentalEventPersistenceOutPort bookRentalEventRepository;
    private BookRentalsMessagingOutPort bookRentalsPublisher;
    private BookRentalsAssembler bookRentalsAssembler;

    @Override
    @SneakyThrows
    public void executeAction(RentalAction rentalAction) {
        log.debug("User {} executes action {} for book {}  ", rentalAction.userId, rentalAction.action, rentalAction.bookId);
        var bookRentals = bookRentalsAssembler.of(rentalAction.bookId);

        bookRentals.trackRentalActivity(userRentalActivity(rentalAction.action, rentalAction.userId));

        bookRentalsRepository.save(bookRentals);
        bookRentalEventRepository.save(new BookRentalEvent(rentalAction));
        bookRentalsPublisher.sendMessage(bookRentals);
    }
}
