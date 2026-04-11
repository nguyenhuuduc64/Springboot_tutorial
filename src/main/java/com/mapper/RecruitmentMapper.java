package com.mapper;

import com.dto.request.RecruitmentRequest;
import com.dto.response.RecruitmentResponse;
import com.entity.Recruitment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RecruitmentMapper {
    void updateRecruitment(@MappingTarget Recruitment recruitment, RecruitmentRequest request);
    RecruitmentResponse toRecruitmentResponse(Recruitment recruitment);
    Recruitment toRecruitment(RecruitmentRequest request);
}
