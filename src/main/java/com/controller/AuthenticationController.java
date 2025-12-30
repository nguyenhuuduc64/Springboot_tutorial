package com.controller;


import com.dto.request.ApiResponse;
import com.dto.request.AuthenticationRequest;
import com.dto.request.IntrospectRequest;
import com.dto.request.LogoutRequest;
import com.dto.response.AuthenticationResponse;
import com.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;
import com.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService  authenticationService;

    @PostMapping("/log-in")

    ApiResponse <AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse result = authenticationService.authenticate(authenticationRequest);
        log.info("login method called");

        return ApiResponse.<AuthenticationResponse>builder()
                .result(AuthenticationResponse.builder()
                        .isAuthenticated(result.isAuthenticated())
                        .token(result.getToken())
                        .build())
                .build();
    }
    @PostMapping("/log-out")
    ApiResponse<Object> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        return authenticationService.logout(request);
    }
    @PostMapping("/introspect")
    ApiResponse <IntrospectResponse> authenticate(@RequestBody IntrospectRequest introspectRequest){
        IntrospectResponse result = authenticationService.introspect(introspectRequest);

        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
