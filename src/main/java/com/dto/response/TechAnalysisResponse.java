package com.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechAnalysisResponse {
    List<String> coreTech;
    List<String> libraries;
    String projectSummary;
    String suggestion; // Gợi ý thêm từ AI để ứng viên cải thiện CV
}
