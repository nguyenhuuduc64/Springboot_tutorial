package com.service;

import com.dto.request.RecruiterRegistrationRequest;
import com.dto.response.RecruiterRegistrationResponse;
import com.entity.RecruiterRegistration;
import com.entity.User;
import com.enums.RequestStatus;
import com.exception.AppException;
import com.exception.ErrorCode;
import com.mapper.RecruiterRegistrationMapper;
import com.repository.RecruiterRegistrationRepository;
import com.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RecruiterRegistrationService {
    UserRepository userRepository;
    RecruiterRegistrationRepository recruiterRegistrationRepository;
    RecruiterRegistrationMapper recruiterRegistrationMapper;
    public RecruiterRegistrationResponse create(RecruiterRegistrationRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.print("user name cua nguoi gui"+  username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        System.out.println("nguoi tao" + user);
        RecruiterRegistration registration = RecruiterRegistration.builder()
                .businessLicenseUrl(request.getBusinessLicenseUrl())
                .note(request.getNote())
                .user(user)
                .status(RequestStatus.PENDING) // Đang chờ duyệt
                // createdAt sẽ tự có nhờ @PrePersist đã viết
                .build();

        RecruiterRegistration saved = recruiterRegistrationRepository.save(registration);

        return RecruiterRegistrationResponse.builder()
                .businessLicenseUrl(saved.getBusinessLicenseUrl())
                .status(saved.getStatus())
                .userId(user.getId())
                .userFullName(user.getFullName())
                .userEmail(user.getEmail())
                .processedAt(null)
                .note(saved.getNote())
                .build();
    }

    // Trong RecruiterRegistrationService.java
    public List<RecruiterRegistrationResponse> getRecruiterRegistrationsByStatus(String statusStr) {
        // 1. Chuyển đổi String FE gửi lên thành Enum của Java
        // Dùng toUpperCase() để tránh lỗi nếu FE gửi chữ thường
        RequestStatus status = RequestStatus.valueOf(statusStr.toUpperCase());

        // 2. Truyền đúng đối tượng Enum vào Repository
        return recruiterRegistrationRepository.findByStatus(status)
                .stream()
                .map(recruiterRegistrationMapper::toRecruiterRegistrationResponse)
                .toList();
    }

    public RecruiterRegistrationResponse updateStatus(String id, RecruiterRegistrationRequest request) {
        RecruiterRegistration registration = recruiterRegistrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn đăng ký này"));

        registration.setStatus(RequestStatus.APPROVED);
        registration.setProcessedAt(LocalDateTime.now());


        RecruiterRegistration saved = recruiterRegistrationRepository.save(registration);


        return recruiterRegistrationMapper.toRecruiterRegistrationResponse(saved);
    }
}
