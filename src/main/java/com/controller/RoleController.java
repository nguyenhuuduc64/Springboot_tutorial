package com.controller;

import com.dto.request.ApiResponse;
import com.dto.request.RoleRequest;
import com.dto.response.RoleResponse;
import com.entity.Role;
import com.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @GetMapping("")
    public ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .code(200)
                .message("get all roles")
                .result(roleService.findAll())
                .build();
    }

    @PostMapping("")
    public ApiResponse<RoleResponse> create(@Valid @RequestBody RoleRequest roleRequest){
        log.info("Role request {}",roleRequest);
        return ApiResponse.<RoleResponse>builder()
                .code(200)
                .message("create role")
                .result(roleService.create(roleRequest))
                .build();
    }

    @DeleteMapping("/{roleId}")
    public void delete(@PathVariable("roleId") String roleId){
        roleService.deleteById(roleId);
    }

}
