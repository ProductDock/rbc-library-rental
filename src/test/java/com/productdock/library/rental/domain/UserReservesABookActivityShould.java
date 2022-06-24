package com.productdock.library.rental.domain;

import com.productdock.library.rental.domain.activity.UserReservesABookActivity;
import com.productdock.library.rental.domain.exception.BookRentalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithReserveRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserReservesABookActivityShould {

    @InjectMocks
    private UserReservesABookActivity userReservesABookActivity;

    @Test
    void reserveABook_whenUserHadReservedItAlready() {
        var previousRecord = Optional.of(bookCopyWithReserveRequest());

        assertThatThrownBy(() -> userReservesABookActivity.changeStatusFrom(previousRecord))
                .isInstanceOf(BookRentalException.class);
    }

    @Test
    void reserveABook_whenUserHadNoInteractionWithItBefore() {
        Optional<BookRentals.BookCopyRentalState> previousRecord = Optional.empty();

        var newRecord = userReservesABookActivity.changeStatusFrom(previousRecord);

        assertThat(newRecord.get().getStatus()).isEqualTo(RentalStatus.RESERVED);
        assertThat(newRecord.get().getPatron()).isNull();
        assertThat(newRecord.get().getDate()).isToday();
    }
}
