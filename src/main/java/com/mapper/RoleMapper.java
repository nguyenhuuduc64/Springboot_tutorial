package com.mapper;


import com.dto.request.RoleRequest;
import com.dto.response.RoleResponse;
import com.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    //do entity Role nhan 1 Set permissions nhung request thi chi co String nen can bo qua field nay
    @Mapping(target = "permissions", ignore = true)
    Role  toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
    //void updateRole(@MappingTarget Role role, RoleRequest request);

}
