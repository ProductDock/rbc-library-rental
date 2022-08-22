package com.productdock.library.rental.application.port.out.web;

import com.productdock.library.rental.domain.UserProfile;

import java.io.IOException;
import java.util.List;

public interface UserProfilesClient {

    List<UserProfile> getUserProfilesByEmails(List<String> emails) throws IOException, InterruptedException;

}
