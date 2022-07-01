package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.data.provider.domain.BookCopyRentalStateMother;
import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.exception.BookRentalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.data.provider.domain.BookCopyRentalStateMother.rentedBookCopy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserCancelsABookReservationActivityShould {

    @InjectMocks
    private UserCancelsABookReservationActivity userCancelsABookReservationActivity;

    @Test
    void cancelBookReservation_whenUserHadNoInteractionWithItBefore() {
        Optional<BookRentals.BookCopyRentalState> previousRentalState = Optional.empty();

        assertThatThrownBy(() -> userCancelsABookReservationActivity.changeStatusFrom(previousRentalState))
                .isInstanceOf(BookRentalException.class);
    }

    @Test
    void cancelBookReservation_whenUserHadNoReservationInteraction() {
        var previousRentalState = Optional.of(rentedBookCopy());

        assertThatThrownBy(() -> userCancelsABookReservationActivity.changeStatusFrom(previousRentalState))
                .isInstanceOf(BookRentalException.class);
    }

    @Test
    void cancelBookReservation_whenUserHaveReservationInteraction() {
        var previousRentalState = BookCopyRentalStateMother.reservedBookCopy();

        var newRecord = userCancelsABookReservationActivity.changeStatusFrom(Optional.of(previousRentalState));

        assertThat(newRecord).isEmpty();
    }
}
