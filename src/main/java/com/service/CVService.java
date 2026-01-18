package com.service;

import com.dto.request.ApiResponse;
import com.dto.request.CVRequest;
import com.dto.response.CVResponse;
import com.entity.CV;
import com.entity.User;
import com.exception.AppException;
import com.exception.ErrorCode;
import com.mapper.CVMapper;
import com.repository.CVRepository;
import com.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@RequiredArgsConstructor
@Service
public class CVService {
    CVRepository cvRepository;
    UserRepository userRepository;
    CVMapper cvMapper;
    public List<CVResponse> getCVs(){
        SecurityContext context = SecurityContextHolder.getContext();

        String username = context.getAuthentication().getName();
        return userRepository.findByUsername(username)
                .map(user -> user.getCvs().stream()
                        .map(cv -> CVResponse.builder()
                                .content(cv.getContent())
                                .build())
                        .toList()
                )
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public CVResponse createCV(CVRequest cvRequest){
        SecurityContext context = SecurityContextHolder.getContext();
        CV cv = cvMapper.toCV(cvRequest);
        String username = context.getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        cv.setUser(user);
        System.out.printf(cv.toString());
        cvRepository.save(cv);
        return cvMapper.toCVResponse(cv);
    }

    public CVResponse updateCV(String id, CVRequest cvRequest){
        SecurityContext context = SecurityContextHolder.getContext();


        if (id == null ||id.isEmpty()){
            return this.createCV(cvRequest);
        } else {
            CV cv = cvRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy CV"));
            cv.setContent(cvRequest.getContent());
            return cvMapper.toCVResponse(cvRepository.save(cv));
        }
    }

}
