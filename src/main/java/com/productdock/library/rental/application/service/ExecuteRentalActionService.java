package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.in.ExecuteRentalActionUseCase;
import com.productdock.library.rental.application.port.out.messaging.BookRentalsMessagingOutPort;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentals;
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
    private BookRentalsMessagingOutPort bookRentalsPublisher;

    @Override
    @SneakyThrows
    public void executeAction(RentalAction rentalAction) {
        log.debug("User {} executes action {} for book {}  ", rentalAction.userId, rentalAction.action, rentalAction.bookId);
        var bookRentals = loadCurrentBookRentals(rentalAction.bookId);

        bookRentals.trackRentalActivity(userRentalActivity(rentalAction.action, rentalAction.userId));

        bookRentalsRepository.save(bookRentals);
        bookRentalsPublisher.sendMessage(bookRentals);
    }

    private BookRentals loadCurrentBookRentals(String bookId) {
        log.debug("Find current book rentals if there are any or start new BookRentals for book {}", bookId);
        return bookRentalsRepository.findByBookId(bookId).orElse(new BookRentals(bookId));
    }

}
