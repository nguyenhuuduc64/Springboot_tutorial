package com.controller;

import com.dto.request.ApiResponse;
import com.dto.request.LikeStoreRequest;
import com.dto.response.LikeStoreResponse;
import com.service.LikeStoreService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class LikeStoreController {

    LikeStoreService likeStoreService;

    @GetMapping("/recruitment/liked")
    public ApiResponse<LikeStoreResponse> getLikeStore() {
        LikeStoreResponse likeStoreResponse = likeStoreService.getRecruitmentByLikeStoreId();
        return ApiResponse.<LikeStoreResponse>builder()
                .code(200)
                .message("get like store successfully")
                .result(likeStoreResponse)
                .build();
    }

    @PostMapping("/likestore/recruitment")
    public ApiResponse<LikeStoreResponse> addRecruitmentToLikeStore (@RequestBody LikeStoreRequest likeStoreRequest) {
        LikeStoreResponse likeStoreResponse = likeStoreService.addRecruimentToLikeStore(likeStoreRequest.getRecruitmentId());
        return ApiResponse.<LikeStoreResponse>builder()
                .code(200)
                .message("add like store successfully")
                .result(likeStoreResponse)
                .build();
    }
}
