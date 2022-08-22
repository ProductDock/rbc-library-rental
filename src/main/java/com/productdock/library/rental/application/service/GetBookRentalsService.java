package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.in.GetBookRentalsQuery;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.application.port.out.web.UserProfilesClient;
import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalWithUserProfile;
import com.productdock.library.rental.domain.UserProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@AllArgsConstructor
class GetBookRentalsService implements GetBookRentalsQuery {

    private BookRentalsPersistenceOutPort rentalRecordRepository;
    private UserProfilesClient userProfilesClient;

    @Override
    public Collection<RentalWithUserProfile> getBookCopiesRentalState(String bookId) throws URISyntaxException, IOException, InterruptedException {
        log.debug("Get book copies from rental record for the {} book", bookId);
        var rentals = getRentalsWithBookId(bookId);
        List<String> userEmails = getUserEmailsFromRentals(rentals);
        var userProfiles = userProfilesClient.getUserProfilesByEmails(userEmails);
        return matchUserProfilesWithRentalRecords(userProfiles, rentals);
    }

    private List<BookRentals.BookCopyRentalState> getRentalsWithBookId(String bookId) {
        return rentalRecordRepository
                .findByBookId(bookId)
                .map(BookRentals::getBookCopiesRentalState)
                .orElse(new ArrayList<>());
    }

    private List<String> getUserEmailsFromRentals(List<BookRentals.BookCopyRentalState> rentals) {
        return rentals.stream().map(BookRentals.BookCopyRentalState::getPatron).toList();
    }

    private List<RentalWithUserProfile> matchUserProfilesWithRentalRecords(List<UserProfile> userProfiles, List<BookRentals.BookCopyRentalState> rentalRecords) {
        var userProfilesMap = createUserProfilesMap(userProfiles);
        List<RentalWithUserProfile> rentalsWithUser = new ArrayList<>();
        for (BookRentals.BookCopyRentalState rentalRecord : rentalRecords) {
            if (userProfilesMap.containsKey(rentalRecord.getPatron())) {
                rentalsWithUser.add(RentalWithUserProfile.builder()
                        .user(userProfilesMap.get(rentalRecord.getPatron()))
                        .status(rentalRecord.getStatus())
                        .date(rentalRecord.getDate())
                        .build());
            }
        }
        return rentalsWithUser;
    }

    private Map<String, UserProfile> createUserProfilesMap(List<UserProfile> userProfiles) {
        return userProfiles.stream().collect(toMap(UserProfile::getEmail, userProfile -> userProfile));
    }
}
