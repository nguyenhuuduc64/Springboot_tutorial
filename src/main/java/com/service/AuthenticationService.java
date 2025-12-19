package com.service;

import com.dto.request.AuthenticationRequest;
import com.dto.request.IntrospectRequest;
import com.dto.response.AuthenticationResponse;
import com.dto.response.IntrospectResponse;
import com.exception.AppException;
import com.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {
    UserRepository userRepository;
    //đánh dấu không inject vào contructor
    @NonFinal
    protected static final String SIGNER_KEY = "PgGjsdBtaJV6LVYM7VIsLjMVTVMZEunePgGjsdBtaJV6LVYM7VIsLjMVTVMZEune1234567890ABCDEF";

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean authenticated =  passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = this.generateToken(user.getUsername());
        boolean isAuthenticated = true;
        return new AuthenticationResponse(token, isAuthenticated);
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        String token = request.getToken();
        try {
            //xem token có do chúng ta cung cấp không
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            //parse token để lấy dữ liệu
            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();



            boolean verified = signedJWT.verify(verifier);
            return IntrospectResponse.builder()
                    .valid((verified && expiryTime.after(new Date())))
                    .build();


        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateToken (String username){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        
        //các thành phần trong 1 jwt
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer(username)
                .issueTime(new Date())
                .expirationTime(new Date(
                        new Date().getTime() + 3600 * 1000
                ))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader,payload);
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return  jwsObject.serialize();
        } catch (JOSEException e){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
