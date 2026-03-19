package com.repository;

import com.entity.RecruiterRegistration;
import com.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruiterRegistrationRepository extends JpaRepository<RecruiterRegistration, String> {
    List<RecruiterRegistration> findByStatus(RequestStatus status);
}
