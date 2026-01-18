package com.repository;

import com.dto.request.CVRequest;
import com.dto.response.CVResponse;
import com.entity.CV;
import com.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CVRepository extends JpaRepository<CV, String> {
}
