package com.application;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface TokenVerifier {
    SignedJWT verifyToken(String token) throws JOSEException, ParseException;
}
