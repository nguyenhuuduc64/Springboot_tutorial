package com.repository;

import com.dto.response.RecruitmentResponse;
import com.entity.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, String> {
    Page<Recruitment> findAll(Pageable pageable);
    List<Recruitment> findByCompanyId(String companyId);
}
