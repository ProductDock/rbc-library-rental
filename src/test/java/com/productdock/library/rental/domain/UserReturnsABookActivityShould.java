package com.productdock.library.rental.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithRentRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserReturnsABookActivityShould {
    
    @InjectMocks
    private UserReturnsABookActivity userReturnsABookActivity;


    @Test
    void returnABookWhenUserHadNoInteractionWithItBefore() {
        Optional<BookRentalRecord.BookCopy> previousRecord = Optional.empty();

        assertThatThrownBy(() -> userReturnsABookActivity.changeStatusFrom(previousRecord))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void returnABookWhenUserHadRentedItAlready() {
        BookRentalRecord.BookCopy previousRecord = bookCopyWithRentRequest();

        var newRecord = userReturnsABookActivity.changeStatusFrom(Optional.of(previousRecord));

        assertThat(newRecord).isEmpty();
    }
}