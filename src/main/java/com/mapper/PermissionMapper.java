package com.mapper;

import com.dto.request.PermissionRequest;
import com.dto.response.PermissionResponse;
import com.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    //tự động nhận request và trả về User
    Permission toPermission(PermissionRequest request);
    //nhận vào 1 Object đã tồn tại và cập nhật lại nó với các field được map
    //void updateUser(@MappingTarget User user, UserUpdateRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
