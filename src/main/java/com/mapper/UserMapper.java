package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.dto.request.UserUpdateRequest;
import com.dto.request.UserCreationRequest;
import com.dto.response.UserResponse;
import com.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    UserResponse toUserResponse(User user);
}
