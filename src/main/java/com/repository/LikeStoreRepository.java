package com.repository;

import com.entity.LikeStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeStoreRepository extends JpaRepository<LikeStore, String> {
    Optional<LikeStore> findByUserId(String userId);
}
