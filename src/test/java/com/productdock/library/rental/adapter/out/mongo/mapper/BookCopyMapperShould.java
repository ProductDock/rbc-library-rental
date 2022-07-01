package com.productdock.library.rental.adapter.out.mongo.mapper;

import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalState;
import com.productdock.library.rental.domain.RentalStatus;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

import static com.productdock.library.rental.data.provider.domain.BookCopyRentalStateMother.rentedBookCopy;
import static org.mockito.Mockito.mock;


class BookCopyMapperShould {

    private final BookCopyMapper bookCopyMapper = Mappers.getMapper(BookCopyMapper.class);

    private static final Date DATE = mock(Date.class);
    private static final String USER_EMAIL = "test@productdock.com";
    private static final RentalStatus RENTAL_STATUS = RentalStatus.RENTED;


    @Test
    void mapDomainBookCopyRentalStateToEntityBookCopyRentalState() {
        var domainRentalState = rentedBookCopy();

        var entityRentalState = bookCopyMapper.toEntity(domainRentalState);

        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(entityRentalState.getStatus()).isEqualTo(domainRentalState.getStatus());
            softly.assertThat(entityRentalState.getDate()).isEqualTo(domainRentalState.getDate());
            softly.assertThat(entityRentalState.getUserEmail()).isEqualTo(domainRentalState.getPatron());
        }
    }

    @Test
    void mapEntityBookCopyRentalStateToDomainBookCopyRentalState() {
        var entityRentalState = new BookCopyRentalState(USER_EMAIL, DATE, RENTAL_STATUS);

        var domainRentalState = bookCopyMapper.toDomain(entityRentalState);

        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(domainRentalState.getStatus()).isEqualTo(entityRentalState.getStatus());
            softly.assertThat(domainRentalState.getDate()).isEqualTo(entityRentalState.getDate());
            softly.assertThat(domainRentalState.getPatron()).isEqualTo(entityRentalState.getUserEmail());
        }
    }
}
