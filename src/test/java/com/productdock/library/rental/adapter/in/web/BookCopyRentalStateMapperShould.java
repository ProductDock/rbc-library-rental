package com.productdock.library.rental.adapter.in.web;

import com.productdock.library.rental.domain.RentalStatus;
import com.productdock.library.rental.domain.RentalWithUserProfile;
import com.productdock.library.rental.domain.UserProfile;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

class BookCopyRentalStateMapperShould {

    private final BookCopyRentalStateMapper bookCopyRentalStateMapper = Mappers.getMapper(BookCopyRentalStateMapper.class);

    public static final String USER_EMAIL = "test@productdock.com";
    public static final String USER_IMAGE = "image";
    public static final String USER_FULL_NAME = "Test test";
    public static final Date DATE = new Date();
    private static final UserProfile USER_PROFILE = UserProfile.builder()
            .email(USER_EMAIL)
            .image(USER_IMAGE)
            .fullName(USER_FULL_NAME)
            .build();
    private static final RentalWithUserProfile RENTAL_WITH_USER_PROFILE = RentalWithUserProfile.builder()
            .status(RentalStatus.RENTED)
            .date(DATE)
            .user(USER_PROFILE)
            .build();


    @Test
    void mapBookCopyRentalStateToBookCopyRentalStateDto() {
        var rentalWithUserProfile = RENTAL_WITH_USER_PROFILE;

        var bookCopyRentalStateDto = bookCopyRentalStateMapper.toDto(rentalWithUserProfile);

        assertThatRentalStatesMatching(rentalWithUserProfile, bookCopyRentalStateDto);
    }

    @Test
    void mapBookCopyRentalStateCollectionToBookCopyRentalStateDtoCollection() {
        var bookCopyRentalStateCollection = List.of(
                RENTAL_WITH_USER_PROFILE,
                RENTAL_WITH_USER_PROFILE);

        var bookCopyRentalStateDtoCollection = bookCopyRentalStateMapper
                .toDtoCollection(bookCopyRentalStateCollection).stream().toList();

        assertThatRentalStatesMatching(bookCopyRentalStateCollection.get(0), bookCopyRentalStateDtoCollection.get(0));
        assertThatRentalStatesMatching(bookCopyRentalStateCollection.get(1), bookCopyRentalStateDtoCollection.get(1));
    }

    private void assertThatRentalStatesMatching(RentalWithUserProfile bookCopyRentalState, BookCopyRentalStateDto bookCopyRentalStateDto) {
        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(bookCopyRentalState.status).isEqualTo(bookCopyRentalStateDto.status);
            softly.assertThat(bookCopyRentalState.user.getEmail()).isEqualTo(bookCopyRentalStateDto.user.email);
            softly.assertThat(bookCopyRentalState.user.getFullName()).isEqualTo(bookCopyRentalStateDto.user.fullName);
            softly.assertThat(bookCopyRentalState.user.getImage()).isEqualTo(bookCopyRentalStateDto.user.image);
            softly.assertThat(bookCopyRentalState.date).isEqualTo(bookCopyRentalStateDto.date);
        }
    }
}
