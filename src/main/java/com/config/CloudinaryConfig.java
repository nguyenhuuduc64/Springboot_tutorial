package com.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dnecovspp", // Thay bằng Cloud Name trên Dashboard Cloudinary
                "api_key", "483859775762866",     // Thay bằng API Key
                "api_secret", "5sUn9vbCWDh-csNtdPpQhD2lTFA" // Thay bằng API Secret
        ));
    }
}