package com.mapper;

import org.mapstruct.*;

import com.dto.request.UserUpdateRequest;
import com.dto.request.UserCreationRequest;
import com.dto.response.UserResponse;
import com.entity.User;


@Mapper(componentModel = "spring")
public interface UserMapper {
    //tự động nhận request và trả về User
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);
    //nhận vào 1 Object đã tồn tại và cập nhật lại nó với các field được map
    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserResponse toUserResponse(User user);
}
