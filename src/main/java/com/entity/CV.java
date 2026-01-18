package com.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String content;
    Date createAt;
    Date updateAt;
    @ManyToOne
    @JoinColumn(name = "user_email") // Tên cột khóa ngoại trong bảng CV
    User user;
}
