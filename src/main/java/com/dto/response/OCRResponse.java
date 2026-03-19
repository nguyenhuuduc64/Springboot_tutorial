package com.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OCRResponse {
    private String taxCode;    // Mã số thuế
    private String companyName; // Tên công ty
    private String address;     // Địa chỉ
    private String rawText;     // Toàn bộ chữ (để đề phòng máy đọc sót)
}
