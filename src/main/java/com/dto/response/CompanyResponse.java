package com.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor  // THÊM DÒNG NÀY THƯA ÔNG CHỦ
@AllArgsConstructor
public class CompanyResponse {
    String id;
    @Column(nullable = false)
    String name;

    @Column(unique = true)
    String taxCode;

    String email;
    String phoneNumber;
    String websiteUrl;
    List<String> address;


    // --- THÊM CÁC TRƯỜNG VỀ THƯƠNG HIỆU ---
    String logo; // Lưu URL ảnh logo
    String banner; // Lưu URL ảnh bìa công ty

    String logoUrl;

    @Column(columnDefinition = "TEXT")
    String description; // Mô tả ngắn gọn

    
}
