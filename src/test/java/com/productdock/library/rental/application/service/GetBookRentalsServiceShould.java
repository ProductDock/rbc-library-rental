package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentals;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetBookRentalsServiceShould {

    private static final List<BookRentals.BookCopyRentalState> COPIES = List.of(new BookRentals.BookCopyRentalState());
    private static final BookRentals BOOK_RENTALS = BookRentals.builder().bookCopiesRentalState(COPIES).build();

    @InjectMocks
    private GetBookRentalsService getBookRentalsService;

    @Mock
    private BookRentalsPersistenceOutPort rentalRecordRepository;

    @ParameterizedTest
    @MethodSource("testArguments")
    void getBookCopiesRentalState_whenNotInRepository(Optional bookRentals, List<BookRentals.BookCopyRentalState> copies) {
        given(rentalRecordRepository.findByBookId(any())).willReturn(bookRentals);

        var bookCopiesRentalState = getBookRentalsService.getBookCopiesRentalState(any());

        assertThat(bookCopiesRentalState).isEqualTo(copies);
    }

    static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of(Optional.empty(), new ArrayList<>()),
                Arguments.of(Optional.of(BOOK_RENTALS), COPIES)
        );
    }
}
