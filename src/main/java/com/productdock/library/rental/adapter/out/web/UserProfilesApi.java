package com.productdock.library.rental.adapter.out.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.rental.application.port.out.web.UserProfilesClient;
import com.productdock.library.rental.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class UserProfilesApi implements UserProfilesClient {

    private String userProfilesServiceUrl;
    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public UserProfilesApi(@Value("${user.profiles.service.url}/api/user-profiles") String userProfilesServiceUrl){
        this.userProfilesServiceUrl = userProfilesServiceUrl;
    }

    @Override
    public List<UserProfile> getUserProfilesByEmails(List<String> emails) throws IOException, InterruptedException {
        var jwt =((Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials()).getTokenValue();
        var uri = new DefaultUriBuilderFactory(userProfilesServiceUrl)
                .builder()
                .queryParam("userEmails", emails)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<List<UserProfile>>(){});
    }
}
