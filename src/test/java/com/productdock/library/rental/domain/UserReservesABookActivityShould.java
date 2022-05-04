package com.productdock.library.rental.domain;

import com.productdock.library.rental.service.RentalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.data.provider.BookCopyMother.defaultBookCopyWithRentRequest;
import static com.productdock.library.rental.data.provider.BookCopyMother.defaultBookCopyWithReserveRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserReservesABookActivityShould {

    @InjectMocks
    private UserReservesABookActivity userReservesABookActivity;

    @Test
    void reserveABookWhenUserHadReservedItAlready() {
        BookRentalRecord.BookCopy previousRecord = defaultBookCopyWithReserveRequest();

        assertThatThrownBy(() -> userReservesABookActivity.changeStatusFrom(Optional.of(previousRecord)))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void reserveABookWhenUserHadNoInteractionWithItBefore() {
        Optional<BookRentalRecord.BookCopy> previousRecord = Optional.empty();

        var newRecord = userReservesABookActivity.changeStatusFrom(previousRecord);

        assertThat(newRecord.get().getStatus()).isEqualTo(RentalStatus.RESERVED);
    }
}
