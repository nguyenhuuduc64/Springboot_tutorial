package com.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeStore {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    User user;

    @ManyToMany
    @JoinTable(
            name = "like_store_details",
            joinColumns = @JoinColumn(name = "like_store_id"),
            inverseJoinColumns = @JoinColumn(name = "recruitment_id")
    )
    List<Recruitment> recruitments;
}