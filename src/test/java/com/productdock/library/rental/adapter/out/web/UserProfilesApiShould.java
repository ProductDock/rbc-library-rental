package com.productdock.library.rental.adapter.out.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.productdock.library.rental.domain.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UserProfilesApiShould {

    private static final List<String> EMAILS = List.of("::email1::");
    private static final List<UserProfile> USER_PROFILES = List.of(UserProfile.builder().build(), UserProfile.builder().build());

    @InjectMocks
    private UserProfilesApi userProfilesApi = new UserProfilesApi("http://any:8080");

    @Mock
    private HttpClient httpClient;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void setupClientRequestWithGivenParams() throws IOException, InterruptedException {
        var httpResponse = mock(HttpResponse.class);
        var securityContext = mock(SecurityContext.class);
        var authentication = mock(Authentication.class);
        var credentials = mock(Jwt.class);
        var token = "token";
        String responseBody = "[{\"fullName\": \"Test test\", \"image\": \"image\", \"email\": \"::email1::\"}, " +
                "{\"fullName\": \"Test test\", \"image\": \"image\", \"email\": \"::email2::\"}]";
        SecurityContextHolder.setContext(securityContext);
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getCredentials()).willReturn(credentials);
        given(credentials.getTokenValue()).willReturn(token);
        given(httpClient.send(any(HttpRequest.class), any())).willReturn(httpResponse);
        given(httpResponse.body()).willReturn(responseBody);

        var userProfiles = userProfilesApi.getUserProfilesByEmails(EMAILS);

        assertThat(userProfiles).hasSize(2);
    }
}
