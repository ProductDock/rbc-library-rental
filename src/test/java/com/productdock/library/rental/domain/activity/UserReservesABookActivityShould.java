package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalStatus;
import com.productdock.library.rental.domain.exception.BookRentalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.data.provider.domain.BookCopyRentalStateMother.reservedBookCopy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserReservesABookActivityShould {

    @InjectMocks
    private UserReservesABookActivity userReservesABookActivity;

    @Test
    void reserveABook_whenUserHadReservedItAlready() {
        var previousRentalState = Optional.of(reservedBookCopy());

        assertThatThrownBy(() -> userReservesABookActivity.changeStatusFrom(previousRentalState))
                .isInstanceOf(BookRentalException.class);
    }

    @Test
    void reserveABook_whenUserHadNoInteractionWithItBefore() {
        Optional<BookRentals.BookCopyRentalState> previousRentalState = Optional.empty();

        var newRentalState = userReservesABookActivity.changeStatusFrom(previousRentalState);

        assertThat(newRentalState.get().getStatus()).isEqualTo(RentalStatus.RESERVED);
        assertThat(newRentalState.get().getPatron()).isNull();
        assertThat(newRentalState.get().getDate()).isToday();
    }
}
