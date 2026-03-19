package com.controller;

import com.dto.request.ApiResponse;
import com.dto.request.RecruiterRegistrationRequest;
import com.dto.response.RecruiterRegistrationResponse;
import com.mapper.RecruiterRegistrationMapper;
import com.service.RecruiterRegistrationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/recruiter")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecruiterRegistrationController {
    RecruiterRegistrationService recruiterRegistrationService;
    RecruiterRegistrationMapper recruiterRegistrationMapper;
    @PostMapping("")
    ApiResponse<RecruiterRegistrationResponse> create(@RequestBody RecruiterRegistrationRequest request){
        RecruiterRegistrationResponse response = recruiterRegistrationService.create(request);
        return ApiResponse.<RecruiterRegistrationResponse>builder()
                .code(200)
                .message("request register recruiter successfully")
                .result(response)
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<RecruiterRegistrationResponse> update(
            @PathVariable String id, // Cần lấy ID từ URL để biết duyệt cho đơn nào
            @RequestBody RecruiterRegistrationRequest request
    ){
        // Gọi qua Service để xử lý logic lưu trữ
        RecruiterRegistrationResponse result = recruiterRegistrationService.updateStatus(id, request);

        return ApiResponse.<RecruiterRegistrationResponse>builder()
                .code(200)
                .message("Cập nhật trạng thái thành công")
                .result(result)
                .build();
    }

    @GetMapping("")
    ApiResponse<List<RecruiterRegistrationResponse>> getRecruiterRegistrations(
            @RequestParam(value = "status", required = false, defaultValue = "PENDING") String status
    ){
        List<RecruiterRegistrationResponse> response = recruiterRegistrationService.getRecruiterRegistrationsByStatus(status);

        return ApiResponse.<List<RecruiterRegistrationResponse>>builder()
                .code(200)
                .result(response)
                .message("get all register registration successfully")
                .build();

    }
}
