package com.config; // hoặc package phù hợp với dự án của bạn

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Lazy
    private final CustomJwtDecoder customJwtDecoder;
    private final String[] PUBLIC_ENDPOINT = {

            "/auth/token",
            "/auth/introspect",
            "/auth/log-in",
            "/auth/log-out",
            "/auth/refresh",
            "/users",
            "/auth/log-in/google",
            "/ai/analyze-tech",

    };
    private final String[] PUBLIC_GET_ENDPOINT = {
            "/cv-components",
    };
    private final String[] PUBLIC_POST_ENDPOINT = {
            "/cv-components",
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //cho pheps tất cả các end point /users đều truy cập được
        httpSecurity
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5173", "https://openedx.id.vn", "https://seeking-job-and-cv-generator-platform-hoh9nf98q.vercel.app/"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(PUBLIC_ENDPOINT).permitAll() // Cho phép tất cả thưa ông chủ
                                .requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINT).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(customJwtDecoder))
                        // QUAN TRỌNG: Thêm cái này để Spring không chặn request khi token lỗi thưa ông chủ
                        .authenticationEntryPoint(new org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint())
                );

        //tắt cross origin
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }


}
