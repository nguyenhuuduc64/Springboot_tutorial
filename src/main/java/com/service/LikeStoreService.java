package com.service;

import com.dto.request.ApiResponse;
import com.dto.response.LikeStoreResponse;
import com.dto.response.RecruitmentResponse;
import com.entity.LikeStore;
import com.entity.Recruitment;
import com.entity.User;
import com.exception.AppException;
import com.exception.ErrorCode;
import com.mapper.LikeStoreMapper;
import com.repository.LikeStoreRepository;
import com.repository.RecruitmentRepository;
import com.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikeStoreService {

    LikeStoreRepository likeStoreRepository;
    RecruitmentRepository recruitmentRepository;
    LikeStoreMapper likeStoreMapper;
    UserRepository userRepository;

    public LikeStoreResponse create() {

        LikeStore likeStore = LikeStore.builder()
                .build();
        return likeStoreMapper.toLikeStoreResponse(likeStoreRepository.save(likeStore));
    }

    public LikeStoreResponse addRecruimentToLikeStore(String recruimentId){
        Recruitment recruitment = recruitmentRepository.findById(recruimentId)
                .orElseThrow(() -> new AppException(ErrorCode.RECRUITMENT_NOT_FOUND));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        LikeStore likeStore = likeStoreRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.LIKE_STORE_NOT_FOUND));

        likeStore.getRecruitments().add(recruitment);
        return likeStoreMapper.toLikeStoreResponse(likeStoreRepository.save(likeStore));

    }

    public LikeStoreResponse getRecruitmentByLikeStoreId(){
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        LikeStore likeStore = likeStoreRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.LIKE_STORE_NOT_FOUND));
        return likeStoreMapper.toLikeStoreResponse(likeStore);
    }

}
