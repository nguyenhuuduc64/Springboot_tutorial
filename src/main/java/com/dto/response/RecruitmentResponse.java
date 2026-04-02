package com.dto.response;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RecruitmentResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;

    String content;

    String salary;

    List<String> requirements;

    List<String> benefits;
    List<String> technologies;

    String workingDay;

    String workingTime;

    String expirationDate;

    String education;

    String level;

    List<String> workingAt;

    CompanyResponse company;

}
