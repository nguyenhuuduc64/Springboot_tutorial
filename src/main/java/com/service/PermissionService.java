package com.service;

import com.dto.request.PermissionRequest;
import com.dto.response.PermissionResponse;
import com.entity.Permission;
import com.mapper.PermissionMapper;
import com.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor // bien nao co defind la final thi se duoc inject vao class
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true) // cac field khong co type thi se mac dinh la

public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;


    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermission() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void deletePermission(String name) {
        permissionRepository.deleteById(name);

    }
}
