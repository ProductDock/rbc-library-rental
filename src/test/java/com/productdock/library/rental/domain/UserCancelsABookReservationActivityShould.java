package com.productdock.library.rental.domain;

import com.productdock.library.rental.exception.BookRentalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithRentRequest;
import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithReserveRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserCancelsABookReservationActivityShould {

    @InjectMocks
    private UserCancelsABookReservationActivity userCancelsABookReservationActivity;

    @Test
    void cancelBookReservation_whenUserHadNoInteractionWithItBefore() {
        Optional<BookRentalRecord.BookCopy> previousRecord = Optional.empty();

        assertThatThrownBy(() -> userCancelsABookReservationActivity.changeStatusFrom(previousRecord))
                .isInstanceOf(BookRentalException.class);
    }

    @Test
    void cancelBookReservation_whenUserHadNoReservationInteraction() {
        var previousRecord = Optional.of(bookCopyWithRentRequest());

        assertThatThrownBy(() -> userCancelsABookReservationActivity.changeStatusFrom(previousRecord))
                .isInstanceOf(BookRentalException.class);
    }

    @Test
    void cancelBookReservation_whenUserHaveReservationInteraction() {
        var previousRecord = bookCopyWithReserveRequest();

        var newRecord = userCancelsABookReservationActivity.changeStatusFrom(Optional.of(previousRecord));

        assertThat(newRecord).isEmpty();
    }
}
