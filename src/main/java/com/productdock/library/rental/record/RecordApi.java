package com.productdock.library.rental.record;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.JSONValue;
import com.productdock.library.rental.exception.NotFoundException;
import com.productdock.library.rental.producer.Publisher;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("api/rental")
public record RecordApi(RecordService recordService) {
    @PostMapping
    public void sendRecord(@RequestBody RecordDTO recordDTO, @RequestHeader("Authorization") String authToken) {
        recordService.create(recordDTO, authToken);
    }
}
