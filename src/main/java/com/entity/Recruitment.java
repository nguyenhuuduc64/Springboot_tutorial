package com.entity;

import com.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Data
@ToString(exclude = {"company", "category"})
@NoArgsConstructor
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;
    @Column(columnDefinition = "TEXT")
    String content;

    String salary;

    @ManyToOne
    @JoinColumn(name = "category_id")
    JobCategory category;

    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company;



    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class) // Nếu ông chủ đang dùng Converter
    private List<String> requirements;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> benefits;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> technologies;

    String workingDay;
    @Column(columnDefinition = "TEXT")
    String workingTime;

    @UpdateTimestamp
    LocalDateTime createAt;

    String expirationDate;

    String education;

    String level;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    List<String> workingAt;
}
