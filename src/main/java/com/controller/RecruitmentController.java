package com.controller;

import com.dto.request.ApiResponse;
import com.dto.response.RecruitmentResponse;
import com.service.RecruitmentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recruit")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RecruitmentController {
    RecruitmentService recruitmentService;
    @GetMapping("")
    public ApiResponse<List<RecruitmentResponse>> getAllRecruitment(){
        return ApiResponse.<List<RecruitmentResponse>>builder()
                .code(200)
                .message("get all recruiment successfully")
                .result(recruitmentService.getAll())
                .build();
    }

}
