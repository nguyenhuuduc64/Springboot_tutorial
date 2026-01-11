package com.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzeRepoRequest {
    private String fileName;
    private String content; // Chuỗi Base64 từ GitHub
}