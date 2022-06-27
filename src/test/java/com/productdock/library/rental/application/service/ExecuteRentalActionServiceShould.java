package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.out.messaging.BookRentalsMessagingOutPort;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalAction;
import com.productdock.library.rental.domain.RentalActionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExecuteRentalActionServiceShould {

    @InjectMocks
    private ExecuteRentalActionService executeRentalActionService;

    @Mock
    private BookRentalsPersistenceOutPort bookRentalsPersistenceOutPort;

    @Mock
    private BookRentalsMessagingOutPort bookRentalsPublisher;

    private static final RentalAction ANY_RENTAL_ACTION = new RentalAction(
            "1",
            "test@productdock.com",
            RentalActionType.RENT);
    private static final BookRentals ANY_BOOK_RENTALS = mock(BookRentals.class);


    @Test
    void verifyIfBookRentalsAreSavedAndPublished() throws Exception {
        given(bookRentalsPersistenceOutPort.findByBookId(ANY_RENTAL_ACTION.bookId)).willReturn(Optional.of(ANY_BOOK_RENTALS));

        executeRentalActionService.executeAction(ANY_RENTAL_ACTION);

        InOrder inOrder = Mockito.inOrder(ANY_BOOK_RENTALS, bookRentalsPersistenceOutPort, bookRentalsPublisher);
        inOrder.verify(ANY_BOOK_RENTALS).trackRentalActivity(any());
        inOrder.verify(bookRentalsPersistenceOutPort).save(ANY_BOOK_RENTALS);
        inOrder.verify(bookRentalsPublisher).sendMessage(ANY_BOOK_RENTALS);
    }
}
