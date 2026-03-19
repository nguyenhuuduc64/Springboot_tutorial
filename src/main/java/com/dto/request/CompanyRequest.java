package com.dto.request;

import com.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyRequest {

    @Column(nullable = false)
    String name;

    @Column(unique = true)
    String taxCode;

    String email;
    String phoneNumber;
    String websiteUrl;
    String address;

    // --- THÊM CÁC TRƯỜNG VỀ THƯƠNG HIỆU ---
    String logo; // Lưu URL ảnh logo
    String banner; // Lưu URL ảnh bìa công ty

    @Column(columnDefinition = "TEXT")
    String description; // Mô tả ngắn gọn

    String logoUrl;
}
