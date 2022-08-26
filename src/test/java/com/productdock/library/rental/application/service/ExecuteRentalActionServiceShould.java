package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.out.messaging.BookRentalsMessagingOutPort;
import com.productdock.library.rental.application.port.out.persistence.BookRentalEventPersistenceOutPort;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentalEvent;
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

    @Mock
    private BookRentalsAssembler bookRentalsAssembler;

    @Mock
    private BookRentalEventPersistenceOutPort bookRentalEventRepository;

    private static final RentalAction ANY_RENTAL_ACTION = RentalAction.builder()
            .bookId("1")
            .action(RentalActionType.RENT).build();
    private static final BookRentals BOOK_RENTALS = mock(BookRentals.class);

    @Test
    void executeAndPublishRentalAction() throws Exception {
        given(bookRentalsAssembler.of(ANY_RENTAL_ACTION.bookId)).willReturn(BOOK_RENTALS);

        executeRentalActionService.executeAction(ANY_RENTAL_ACTION);

        InOrder inOrder = Mockito.inOrder(BOOK_RENTALS, bookRentalsRepository, bookRentalEventRepository, bookRentalsPublisher);
        inOrder.verify(BOOK_RENTALS).trackRentalActivity(any());
        inOrder.verify(bookRentalsRepository).save(BOOK_RENTALS);
        inOrder.verify(bookRentalEventRepository).save(any(BookRentalEvent.class));
        inOrder.verify(bookRentalsPublisher).sendMessage(BOOK_RENTALS);
    }

}
