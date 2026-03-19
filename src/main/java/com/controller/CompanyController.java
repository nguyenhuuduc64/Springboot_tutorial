package com.controller;


import com.dto.request.ApiResponse;
import com.dto.request.CompanyRequest;
import com.dto.response.CompanyResponse;
import com.service.CompanyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyController {
    CompanyService companyService;
    @PostMapping("")
    ApiResponse<CompanyResponse> createCompany(
            @RequestParam("userId") String userId,
            @RequestBody CompanyRequest request
    ) {
        return ApiResponse.<CompanyResponse>builder()
                .result(companyService.createCompany(userId, request))
                .build();
    }
    @PutMapping("/{companyId}")
    public ApiResponse<CompanyResponse> edit(@RequestBody CompanyRequest request, @PathVariable("companyId") String companyId){
        log.info("day la company gui len {}", request);
        return ApiResponse.<CompanyResponse>builder()
                .code(200)
                .message("edit company successfully")
                .result(companyService.edit(request, companyId))
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<CompanyResponse> getCompanyByUserId(@PathVariable("userId") String userId){
        return ApiResponse.<CompanyResponse>builder()
                .code(200)
                .message("get company by user id successfully")
                .result(companyService.getCompanyByUserId(userId))
                .build();
    }

}
