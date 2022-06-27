package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalStatus;
import com.productdock.library.rental.domain.exception.BookRentalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.data.provider.domain.BookCopyMother.bookCopyRentalStateWithRentRequest;
import static com.productdock.library.rental.data.provider.domain.BookCopyMother.bookCopyRentalStateWithReserveRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserBorrowsABookActivityShould {

    @InjectMocks
    private UserBorrowsABookActivity userBorrowsABookActivity;

    @Test
    void rentABook_whenUserHadReservedItAlready() {
        var previousRentalState = bookCopyRentalStateWithReserveRequest();

        var newRentalState = userBorrowsABookActivity.changeStatusFrom(Optional.of(previousRentalState));

        assertThat(newRentalState.get().getStatus()).isEqualTo(RentalStatus.RENTED);
        assertThat(newRentalState.get().getPatron()).isNull();
        assertThat(newRentalState.get().getDate()).isToday();
    }

    @Test
    void rentABook_whenUserHadNoInteractionWithItBefore() {
        Optional<BookRentals.BookCopyRentalState> previousRentalState = Optional.empty();

        var newRentalState = userBorrowsABookActivity.changeStatusFrom(previousRentalState);

        assertThat(newRentalState.get().getStatus()).isEqualTo(RentalStatus.RENTED);
    }

    @Test
    void rentABook_whenUserHadRentedItAlready() {
        var previousRentalState = Optional.of(bookCopyRentalStateWithRentRequest());

        assertThatThrownBy(() -> userBorrowsABookActivity.changeStatusFrom(previousRentalState))
                .isInstanceOf(BookRentalException.class);
    }
}
