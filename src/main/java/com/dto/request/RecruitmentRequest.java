package com.dto.request;

import com.entity.Company;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RecruitmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;

    String content;

    String salary;

    String categoryId;

    List<String> requirements;

    List<String> benefits;
    List<String> technologies;

    String workingDay;

    String workingTime;

    String expirationDate;

    String education;

    String level;

    List<String> workingAt;

}
