package com.productdock.library.rental.adapter.out.mongo.mapper;

import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalState;
import com.productdock.library.rental.adapter.out.mongo.entity.BookRentalState;
import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalStatus;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.productdock.library.rental.data.provider.domain.BookRentalsMother.bookRentalsWithRentRequest;
import static com.productdock.library.rental.data.provider.out.mongo.BookRentalStateMother.bookRentalStateWithRentalRequest;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BookRentalStateMapperImpl.class, BookCopyMapperImpl.class})
class BookRentalStateMapperShould {

    @Autowired
    private BookRentalStateMapper bookRentalStateMapper;

    @Test
    void mapBookRentalsToBookRentalState() {
        var bookRentals = bookRentalsWithRentRequest();

        var bookRentalState = bookRentalStateMapper.toEntity(bookRentals);

        assertThatMatching(bookRentals, bookRentalState);
    }

    @Test
    void mapBookRentalStateToBookRentals() {
        var bookRentalState = bookRentalStateWithRentalRequest(RentalStatus.RENTED);

        var bookRentals = bookRentalStateMapper.toDomain(bookRentalState);

        assertThatMatching(bookRentals, bookRentalState);
    }

    @Test
    void mapBookRentalStateCollectionToBookRentalsCollection() {
        var bookRentalStates = List.of(
                bookRentalStateWithRentalRequest(RentalStatus.RENTED),
                bookRentalStateWithRentalRequest(RentalStatus.RENTED)
        );

        var bookRentalsCollection = bookRentalStateMapper.toDomainCollection(bookRentalStates).stream().toList();

        assertThatMatching(bookRentalsCollection.get(0), bookRentalStates.get(0));
        assertThatMatching(bookRentalsCollection.get(1), bookRentalStates.get(1));

    }

    private void assertThatMatching(BookRentals bookRentals,
                                    BookRentalState bookRentalState) {
        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(bookRentals.getBookId()).isEqualTo(bookRentalState.getBookId());
            assertThatBookCopiesRentalStateAreMatching(bookRentals.getBookCopiesRentalState(),
                    bookRentalState.getBookCopiesRentalState());
        }
    }

    private void assertThatBookCopiesRentalStateAreMatching(List<BookRentals.BookCopyRentalState> domainCopiesRentalState,
                                                            List<BookCopyRentalState> entityCopiesRentalState) {
        assertThat(domainCopiesRentalState).hasSameSizeAs(entityCopiesRentalState);
        var entityRentalState = entityCopiesRentalState.get(0);
        var domainRentalState = domainCopiesRentalState.get(0);
        assertThatRentalStatesAreMatching(domainRentalState, entityRentalState);
    }

    private void assertThatRentalStatesAreMatching(BookRentals.BookCopyRentalState domainRentalState,
                                                   BookCopyRentalState entityRentalState) {
        assertThat(domainRentalState.getPatron()).isEqualTo(entityRentalState.getUserEmail());
        assertThat(domainRentalState.getStatus()).isEqualTo(entityRentalState.getStatus());
        assertThat(domainRentalState.getDate()).isEqualTo(entityRentalState.getDate());
    }

}
