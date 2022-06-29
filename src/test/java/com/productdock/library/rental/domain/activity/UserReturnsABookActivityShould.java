package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.exception.BookRentalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.data.provider.domain.BookCopyRentalStateMother.bookCopyRentalStateWithRentRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserReturnsABookActivityShould {

    @InjectMocks
    private UserReturnsABookActivity userReturnsABookActivity;


    @Test
    void returnABook_whenUserHadNoInteractionWithItBefore() {
        Optional<BookRentals.BookCopyRentalState> previousRentalState = Optional.empty();

        assertThatThrownBy(() -> userReturnsABookActivity.changeStatusFrom(previousRentalState))
                .isInstanceOf(BookRentalException.class);
    }

    @Test
    void returnABook_whenUserHadRentedItAlready() {
        var previousRentalState = bookCopyRentalStateWithRentRequest();

        var newRentalState = userReturnsABookActivity.changeStatusFrom(Optional.of(previousRentalState));

        assertThat(newRentalState).isEmpty();
    }
}
