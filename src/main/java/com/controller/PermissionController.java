package com.controller;


import com.dto.request.ApiResponse;
import com.dto.request.PermissionRequest;
import com.dto.response.PermissionResponse;
import com.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService  permissionService;

    @PostMapping("")
    ApiResponse<PermissionResponse> createPermission(@RequestBody  PermissionRequest request){
        PermissionResponse permission = permissionService.createPermission(request);
        return ApiResponse.<PermissionResponse>builder()
                .code(200)
                .result(permission)
                .message("success")
                .build();
    }

    @GetMapping("")
    ApiResponse<List<PermissionResponse>> getAllPermission(){
        List<PermissionResponse> permissionResponseList = permissionService.getAllPermission();
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .message("get all permission")
                .result(permissionResponseList)
                .build();
    }
    @DeleteMapping("/{permissionId}")
    ApiResponse<Void> deletePermission(@PathVariable("permissionId") String permissionId){
        permissionService.deletePermission(permissionId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("delete permission")
                .build();
    }
    
}
