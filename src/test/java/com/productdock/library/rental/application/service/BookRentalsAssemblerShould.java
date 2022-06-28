package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookRentalsAssemblerShould {

    private static final BookRentals BOOK_RENTALS = Mockito.mock(BookRentals.class);

    @InjectMocks
    private BookRentalsAssembler bookRentalsAssembler;

    @Mock
    private BookRentalsPersistenceOutPort bookRentalsRepository;

    @Test
    void assembleDomain_whenNotInRepository() {
        given(bookRentalsRepository.findByBookId("1")).willReturn(Optional.empty());

        var bookRentals = bookRentalsAssembler.of("1");

        assertThat(bookRentals).isNotNull();
        assertThat(bookRentals.getBookId()).isEqualTo("1");
    }

    @Test
    void assembleDomain_whenInRepository() {
        given(bookRentalsRepository.findByBookId("2")).willReturn(Optional.of(BOOK_RENTALS));

        var bookRentals = bookRentalsAssembler.of("2");

        assertThat(bookRentals).isEqualTo(BOOK_RENTALS);
    }
}
