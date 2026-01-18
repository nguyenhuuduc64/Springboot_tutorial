package com.controller;

import com.dto.request.ApiResponse;
import com.dto.request.CVRequest;
import com.dto.response.CVResponse;
import com.repository.CVRepository;
import com.repository.UserRepository;
import com.service.CVService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cvs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CVController {

    CVRepository cvRepository;
    UserRepository userRepository;
    CVService cvService;
    @GetMapping("")
    public ApiResponse<List<CVResponse>> getCV(){
        System.out.println("getCV");
        return ApiResponse.<List<CVResponse>>builder()
                .code(200)
                .result(cvService.getCVs()) // Giờ đã đúng kiểu List<CVResponse>
                .message("get all cvs successfully")
                .build();
    }

    @PostMapping("")
    public ApiResponse<CVResponse> createCV(@RequestBody CVRequest cvRequest){
        return ApiResponse.<CVResponse>builder()
                .message("create cv successfully")
                .code(200)
                .result(cvService.createCV(cvRequest))
                .build();
    }

    @PutMapping({"", "/{id}"})
    public ApiResponse<CVResponse> updateCV(@PathVariable(name = "id", required = false) String id,@RequestBody CVRequest cvRequest){
        return ApiResponse.<CVResponse>builder()
                .message("update cv successfully")
                .code(200)
                .result(cvService.updateCV(id,cvRequest))
                .build();
    }
}
