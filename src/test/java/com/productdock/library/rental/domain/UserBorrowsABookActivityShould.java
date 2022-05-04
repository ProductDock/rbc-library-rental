package com.productdock.library.rental.domain;

import com.productdock.library.rental.service.RentalStatus;
import kafka.coordinator.group.Empty;
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
class UserBorrowsABookActivityShould {

    @InjectMocks
    private UserBorrowsABookActivity userBorrowsABookActivity;

    @Test
    void rentABookWhenUserHadReservedItAlready() {
        BookRentalRecord.BookCopy previousRecord = defaultBookCopyWithReserveRequest();

        var newRecord = userBorrowsABookActivity.changeStatusFrom(Optional.of(previousRecord));

        assertThat(newRecord.get().getStatus()).isEqualTo(RentalStatus.RENTED);
    }

    @Test
    void rentABookWhenUserHadNoInteractionWithItBefore() {
        Optional<BookRentalRecord.BookCopy> previousRecord = Optional.empty();

        var newRecord = userBorrowsABookActivity.changeStatusFrom(previousRecord);

        assertThat(newRecord.get().getStatus()).isEqualTo(RentalStatus.RENTED);
    }

    @Test
    void rentABookWhenUserHadRentedItAlready() {
        BookRentalRecord.BookCopy previousRecord = defaultBookCopyWithRentRequest();

        assertThatThrownBy(() -> userBorrowsABookActivity.changeStatusFrom(Optional.of(previousRecord)))
                .isInstanceOf(RuntimeException.class);
    }
}
