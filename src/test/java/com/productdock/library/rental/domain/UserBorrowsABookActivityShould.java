package com.productdock.library.rental.domain;

import com.productdock.library.rental.exception.BookRentalException;
import com.productdock.library.rental.service.RentalStatus;
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
class UserBorrowsABookActivityShould {

    @InjectMocks
    private UserBorrowsABookActivity userBorrowsABookActivity;

    @Test
    void rentABook_whenUserHadReservedItAlready() {
        var previousRecord = bookCopyWithReserveRequest();

        var newRecord = userBorrowsABookActivity.changeStatusFrom(Optional.of(previousRecord));

        assertThat(newRecord.get().getStatus()).isEqualTo(RentalStatus.RENTED);
        assertThat(newRecord.get().getPatron()).isNull();
        assertThat(newRecord.get().getDate()).isToday();
    }

    @Test
    void rentABook_whenUserHadNoInteractionWithItBefore() {
        Optional<BookRentalRecord.BookCopy> previousRecord = Optional.empty();

        var newRecord = userBorrowsABookActivity.changeStatusFrom(previousRecord);

        assertThat(newRecord.get().getStatus()).isEqualTo(RentalStatus.RENTED);
    }

    @Test
    void rentABook_whenUserHadRentedItAlready() {
        var previousRecord = Optional.of(bookCopyWithRentRequest());

        assertThatThrownBy(() -> userBorrowsABookActivity.changeStatusFrom(previousRecord))
                .isInstanceOf(BookRentalException.class);
    }
}
