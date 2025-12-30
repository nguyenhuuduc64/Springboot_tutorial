package com.service;

import com.dto.request.*;
import com.dto.response.AuthenticationResponse;
import com.dto.response.IntrospectResponse;
import com.dto.response.RefreshResponse;
import com.entity.InvalidatedToken;
import com.entity.User;
import com.exception.AppException;
import com.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.repository.InvalidatedTokenRepository;
import com.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository  invalidatedTokenRepository;
    private final JwtDecoder jwtDecoder;

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
        String token = this.generateToken(user);
        boolean isAuthenticated = true;
        return new AuthenticationResponse(token, isAuthenticated);
    }

    public ApiResponse<Object> logout(LogoutRequest request) throws ParseException, JOSEException {
        var signedJwt = verifyToken(request.getToken());

        String jti = signedJwt.getJWTClaimsSet().getJWTID();

        Date expiry = signedJwt.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expiryTime(expiry)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
        return ApiResponse.builder()
                .code(200)
                .message("logout successfully")
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        String token = request.getToken();
        try {
            SignedJWT signJwt = verifyToken(token);

            return IntrospectResponse.builder()
                    .valid(true)
                    .build();


        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateToken (User user){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        
        //các thành phần trong 1 jwt
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer(user.getUsername())
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
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

    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {


            //xem token có do chúng ta cung cấp không
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            //parse token để lấy dữ liệu
            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            boolean verified = signedJWT.verify(verifier);

            if (!verified | expiryTime.before(new Date())) throw new AppException(ErrorCode.TOKEN_EXPIRED);

            if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
                throw new AppException(ErrorCode.UNAUTHENTICATED);

            return signedJWT;

    }



    /*public RefreshResponse refreshToken(RefreshRequest request){
        var signJWT = verifyToken(request.getToken());

        var jit = signJWT.getId();
        var expireTime = signJWT.getExpiresAt();


    }*/

    private String buildScope(User user){
        StringJoiner scopes =  new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> scopes.add(role.getName()));
        }

        return scopes.toString();

    }
}
