package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.out.messaging.BookRentalsMessagingOutPort;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.ds.RentalAction;
import com.productdock.library.rental.domain.ds.RentalActionType;
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
    private BookRentalsPersistenceOutPort bookRentalsRepository;

    @Mock
    private BookRentalsMessagingOutPort bookRentalsPublisher;

    private static final RentalAction ANY_RENTAL_ACTION = RentalAction.builder()
            .action(RentalActionType.RENT).build();
    private static final BookRentals ANY_BOOK_RENTALS = mock(BookRentals.class);

    @Test
    void verifyIfBookRentalsAreSavedAndPublished() throws Exception {
        given(bookRentalsRepository.findByBookId(ANY_RENTAL_ACTION.bookId)).willReturn(Optional.of(ANY_BOOK_RENTALS));

        executeRentalActionService.executeAction(ANY_RENTAL_ACTION);

        InOrder inOrder = Mockito.inOrder(ANY_BOOK_RENTALS, bookRentalsRepository, bookRentalsPublisher);
        inOrder.verify(ANY_BOOK_RENTALS).trackRentalActivity(any());
        inOrder.verify(bookRentalsRepository).save(ANY_BOOK_RENTALS);
        inOrder.verify(bookRentalsPublisher).sendMessage(ANY_BOOK_RENTALS);
    }
}
