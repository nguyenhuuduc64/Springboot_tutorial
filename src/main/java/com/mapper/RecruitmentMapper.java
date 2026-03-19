package com.mapper;

import com.dto.response.RecruitmentResponse;
import com.entity.Recruitment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecruitmentMapper {
    RecruitmentResponse toRecruitmentResponse(Recruitment recruitment);

}
