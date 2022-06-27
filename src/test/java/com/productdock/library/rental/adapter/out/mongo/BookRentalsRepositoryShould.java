package com.productdock.library.rental.adapter.out.mongo;

import com.productdock.library.rental.adapter.out.mongo.entity.BookRentalState;
import com.productdock.library.rental.adapter.out.mongo.mapper.BookRentalStateMapper;
import com.productdock.library.rental.domain.BookRentals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class BookRentalsRepositoryShould {

    private static final String BOOK_ID = "1";
    private static final Collection<BookRentalState> BOOK_RENTAL_STATE_WITH_RESERVATIONS =
            List.of(mock(BookRentalState.class),
                    mock(BookRentalState.class));
    private static final Collection<BookRentals> BOOK_RENTALS_WITH_RESERVATIONS =
            List.of(mock(BookRentals.class),
                    mock(BookRentals.class));
    private static final Optional<BookRentalState> BOOK_RENTAL_STATE = Optional.of(mock(BookRentalState.class));
    private static final Optional<BookRentalState> PREVIOUS_BOOK_RENTAL_STATE = Optional.of(mock(BookRentalState.class));

    private static final BookRentals BOOK_RENTALS = mock(BookRentals.class);

    @InjectMocks
    private BookRentalsRepository bookRentalsRepository;

    @Mock
    private BookRentalStateRepository bookRentalStateRepository;

    @Mock
    private BookRentalStateMapper bookRentalStateMapper;

    @Test
    void findBookRentalsWhenIdExist() {
        given(bookRentalStateRepository.findByBookId(BOOK_ID)).willReturn(BOOK_RENTAL_STATE);
        given(bookRentalStateMapper.toDomain(BOOK_RENTAL_STATE.get())).willReturn(BOOK_RENTALS);

        var bookRentals = bookRentalsRepository.findByBookId(BOOK_ID);

        assertThat(bookRentals).isPresent();
        assertThat(bookRentals.get()).isEqualTo(BOOK_RENTALS);
    }

    @Test
    void findBookRentalsWhenIdNotExist() {
        given(bookRentalStateRepository.findByBookId(BOOK_ID)).willReturn(Optional.empty());

        var bookRentals = bookRentalsRepository.findByBookId(BOOK_ID);

        assertThat(bookRentals).isEmpty();
    }

    @Test
    void findBookRentalsWithReservations() {
        given(bookRentalStateRepository.findWithReservations()).willReturn(BOOK_RENTAL_STATE_WITH_RESERVATIONS);
        given(bookRentalStateMapper.toDomainCollection(BOOK_RENTAL_STATE_WITH_RESERVATIONS)).willReturn(BOOK_RENTALS_WITH_RESERVATIONS);

        var bookRentals = bookRentalsRepository.findWithReservations();

        assertThat(bookRentals).containsExactlyInAnyOrderElementsOf(BOOK_RENTALS_WITH_RESERVATIONS);
    }

    @Test
    void saveBookRentals() {
        given(bookRentalStateRepository.findByBookId(BOOK_RENTALS.getBookId())).willReturn(Optional.empty());
        given(bookRentalStateMapper.toEntity(BOOK_RENTALS)).willReturn(BOOK_RENTAL_STATE.get());

        bookRentalsRepository.save(BOOK_RENTALS);

        verify(bookRentalStateRepository).save(BOOK_RENTAL_STATE.get());
    }

    @Test
    void saveBookRentalsAndSetIdWhenPreviousRentalsExist() {
        given(bookRentalStateRepository.findByBookId(BOOK_RENTALS.getBookId())).willReturn(PREVIOUS_BOOK_RENTAL_STATE);
        given(bookRentalStateMapper.toEntity(BOOK_RENTALS)).willReturn(BOOK_RENTAL_STATE.get());

        bookRentalsRepository.save(BOOK_RENTALS);

        verify(BOOK_RENTAL_STATE.get()).setId(PREVIOUS_BOOK_RENTAL_STATE.get().getId());
        verify(bookRentalStateRepository).save(BOOK_RENTAL_STATE.get());
    }
}
