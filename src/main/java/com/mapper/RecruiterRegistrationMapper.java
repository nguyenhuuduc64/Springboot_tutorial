package com.mapper;

import com.dto.request.RecruiterRegistrationRequest;
import com.dto.response.RecruiterRegistrationResponse;
import com.entity.RecruiterRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecruiterRegistrationMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", source = "user.fullName")
    @Mapping(target = "userEmail", source = "user.email")
    RecruiterRegistrationResponse toRecruiterRegistrationResponse(RecruiterRegistration recruiterRegistration);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "processedAt", ignore = true)
    RecruiterRegistration toReRecruiterRegistration(RecruiterRegistrationRequest recruiterRegistrationRequest);
}
