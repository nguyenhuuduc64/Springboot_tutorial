package com.service;

import com.dto.request.ApiResponse;
import com.dto.request.RoleRequest;
import com.dto.response.RoleResponse;
import com.entity.Role;
import com.mapper.RoleMapper;
import com.repository.PermissionRepository;
import com.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor // bien nao co defind la final thi se duoc inject vao class
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true) // cac field khong co type thi se mac dinh la
// private va dua vao contructor nhu final
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        //do permissions la List nhung chung ta can Set nen chuyen tu List ve Set thong qua HashSet
        //vi set la Interface khong the khoi tao bang tu khoa new

        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteById(String id) {
        roleRepository.deleteById(id);
    }
}
