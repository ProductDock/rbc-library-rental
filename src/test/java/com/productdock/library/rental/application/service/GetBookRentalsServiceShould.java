package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.application.port.out.web.UserProfilesClient;
import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalWithUserProfile;
import com.productdock.library.rental.domain.UserProfile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetBookRentalsServiceShould {

    private static final String USER_EMAIL = "test@productdock.com";
    private static final UserProfile USER_PROFILE = UserProfile.builder().email(USER_EMAIL).build();
    private static final List<UserProfile> USER_PROFILES = List.of(USER_PROFILE);
    private static final List<String> USER_EMAILS = List.of(USER_EMAIL);
    private static final List<RentalWithUserProfile> RENTALS_WITH_USER_PROFILES = List.of(RentalWithUserProfile.builder().user(USER_PROFILE).build());
    private static final List<BookRentals.BookCopyRentalState> BOOK_COPIES = List.of(BookRentals.BookCopyRentalState.builder().patron(USER_EMAIL).build());
    private static final BookRentals BOOK_RENTALS = BookRentals.builder().bookCopiesRentalState(BOOK_COPIES).build();

    @InjectMocks
    private GetBookRentalsService getBookRentalsService;

    @Mock
    private BookRentalsPersistenceOutPort rentalRecordRepository;

    @Mock
    private UserProfilesClient userProfilesClient;

    @SneakyThrows
    @Test
    void getBookCopiesRentalState_whenNotInRepository() {
        given(rentalRecordRepository.findByBookId(any())).willReturn(Optional.empty());

        var bookCopiesRentalState = getBookRentalsService.getBookCopiesRentalState(any());

        assertThat(bookCopiesRentalState).isEmpty();
    }

    @SneakyThrows
    @Test
    void getBookCopiesRentalState_whenInRepository() {
        var bookRentals = Optional.of(BOOK_RENTALS);
        given(rentalRecordRepository.findByBookId(any())).willReturn(bookRentals);
        given(userProfilesClient.getUserProfilesByEmails(USER_EMAILS)).willReturn(USER_PROFILES);

        var bookCopiesRentalState = getBookRentalsService.getBookCopiesRentalState(any());

        assertThat(bookCopiesRentalState).hasSize(RENTALS_WITH_USER_PROFILES.size());
    }
}
