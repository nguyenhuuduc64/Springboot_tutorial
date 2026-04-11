package com.controller;

import com.dto.request.ApiResponse;
import com.dto.request.RecruitmentRequest;
import com.dto.response.RecruitmentResponse;
import com.entity.Recruitment;
import com.service.RecruitmentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/recruitment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RecruitmentController {
    RecruitmentService recruitmentService;
    /*@GetMapping("")
    public ApiResponse<List<RecruitmentResponse>> getAllRecruitment(){
        return ApiResponse.<List<RecruitmentResponse>>builder()
                .code(200)
                .message("get all recruiment successfully")
                .result(recruitmentService.getAll())
                .build();
    }*/

    @GetMapping
    public ApiResponse<Page<Recruitment>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<Page<Recruitment>>builder()
                .code(200)
                .result((recruitmentService.getAllRecruitments(page, size)))
                .message("get recruitment successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RecruitmentResponse> getRecruitment(@PathVariable("id") String id){
        return ApiResponse.<RecruitmentResponse>builder()
                .code(200)
                .message("get recruiment successfully")
                .result(recruitmentService.getRecruitment(id))
                .build();
    }

    @GetMapping("/company/{id}")
    public ApiResponse<List<RecruitmentResponse>> getRecruitmentByCompanyId(@PathVariable("id") String companyId){
        return ApiResponse.<List<RecruitmentResponse>>builder()
                .code(200)
                .message("get recruiment successfully")
                .result(recruitmentService.getRecruitmentByCompanyId(companyId))
                .build();
    }

    @PostMapping("")
    public ApiResponse<RecruitmentResponse> create(@RequestBody RecruitmentRequest request){
        log.info("recruitment request cotroller: {}", request);
        return ApiResponse.<RecruitmentResponse>builder()
                .message("Create recruitment successfully")
                .code(200)
                .result(recruitmentService.create(request))
                .build();
    }

    @DeleteMapping("/{recruitmentId}")
    public String delete(@PathVariable String recruitmentId){
        return recruitmentService.delete(recruitmentId);
    }

    @PutMapping("/{recruitmentId}")
    public ApiResponse<RecruitmentResponse> update(@PathVariable String recruitmentId, @RequestBody RecruitmentRequest request){
        return ApiResponse.<RecruitmentResponse>builder()
                .message("update recruitment successfully")
                .code(200)
                .result(recruitmentService.update(recruitmentId, request))
                .build();
    }

}
