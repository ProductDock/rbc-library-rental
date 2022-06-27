package com.productdock.library.rental.adapter.in.web;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalStatus;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BookCopyRentalStateMapperImpl.class})
class BookCopyRentalStateMapperShould {

    public static final String USER_EMAIL = "test@productdock.com";
    public static final Date DATE = new Date();
    private static final BookRentals.BookCopyRentalState BOOK_COPY_RENTED_RENTAL_STATE = BookRentals.BookCopyRentalState.builder()
            .status(RentalStatus.RENTED)
            .date(DATE)
            .patron(USER_EMAIL)
            .build();


    @Autowired
    private BookCopyRentalStateMapper bookCopyRentalStateMapper;

    @Test
    void mapBookCopyRentalStateToBookCopyRentalStateDto() {
        var bookCopyRentalState = BOOK_COPY_RENTED_RENTAL_STATE;

        var bookCopyRentalStateDto = bookCopyRentalStateMapper.toDto(bookCopyRentalState);

        assertThatRentalStatesMatching(bookCopyRentalState, bookCopyRentalStateDto);
    }

    @Test
    void mapBookCopyRentalStateCollectionToBookCopyRentalStateDtoCollection() {
        var bookCopyRentalStateCollection = List.of(
                BOOK_COPY_RENTED_RENTAL_STATE,
                BOOK_COPY_RENTED_RENTAL_STATE);

        var bookCopyRentalStateDtoCollection = bookCopyRentalStateMapper
                .toDtoCollection(bookCopyRentalStateCollection).stream().toList();

        assertThatRentalStatesMatching(bookCopyRentalStateCollection.get(0), bookCopyRentalStateDtoCollection.get(0));
        assertThatRentalStatesMatching(bookCopyRentalStateCollection.get(1), bookCopyRentalStateDtoCollection.get(1));
    }

    private void assertThatRentalStatesMatching(BookRentals.BookCopyRentalState bookCopyRentalState, BookCopyRentalStateDto bookCopyRentalStateDto) {
        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(bookCopyRentalState.getStatus()).isEqualTo(bookCopyRentalStateDto.status);
            softly.assertThat(bookCopyRentalState.getPatron()).isEqualTo(bookCopyRentalStateDto.email);
        }
    }
}
