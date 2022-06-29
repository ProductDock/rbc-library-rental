package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalStatus;
import com.productdock.library.rental.domain.exception.BookRentalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.data.provider.domain.BookCopyRentalStateMother.rentedBookCopy;
import static com.productdock.library.rental.data.provider.domain.BookCopyRentalStateMother.reservedBookCopy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserBorrowsABookActivityShould {

    @InjectMocks
    private UserBorrowsABookActivity userBorrowsABookActivity;

    @Test
    void rentABook_whenUserHadReservedItAlready() {
        var newRentalState = userBorrowsABookActivity.changeStatusFrom(Optional.of(reservedBookCopy()));

        assertThat(newRentalState.get().getStatus()).isEqualTo(RentalStatus.RENTED);
        assertThat(newRentalState.get().getPatron()).isNull();
        assertThat(newRentalState.get().getDate()).isToday();
    }

    @Test
    void rentABook_whenUserHadNoInteractionWithItBefore() {
        var newRentalState = userBorrowsABookActivity.changeStatusFrom(Optional.empty());

        assertThat(newRentalState.get().getStatus()).isEqualTo(RentalStatus.RENTED);
    }

    @Test
    void rentABook_whenUserHadRentedItAlready() {
        Optional<BookRentals.BookCopyRentalState> previousState = Optional.of(rentedBookCopy());

        assertThatThrownBy(() -> userBorrowsABookActivity.changeStatusFrom(previousState))
                .isInstanceOf(BookRentalException.class);
    }
}
