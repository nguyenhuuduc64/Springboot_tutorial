package com.config; // hoặc package phù hợp với dự án của bạn

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINT = {

            "/auth/token",
            "/auth/introspect",
            "/auth/log-in"
    };


    @Value("${jwt.signerKey}")
    private String jwtSignerKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //cho pheps tất cả các end point /users đều truy cập được
        httpSecurity.authorizeHttpRequests(request ->
            request
                    //tat ca endpoint nam trong public voi method POST thi public
                    .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll()
                    //.requestMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("SCOPE_ADMIN")
                    .anyRequest().authenticated()
        );
        httpSecurity.oauth2ResourceServer(request ->
            request
                    .jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        );
        //tắt cross origin
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {

        SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSignerKey.getBytes(), "HS512");

        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    };

}
