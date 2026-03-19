package com.dto.request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

}
