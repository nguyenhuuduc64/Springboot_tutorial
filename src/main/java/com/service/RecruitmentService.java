package com.service;

import com.dto.response.RecruitmentResponse;
import com.entity.Recruitment;
import com.mapper.RecruitmentMapper;
import com.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitmentService {
    RecruitmentRepository recruitmentRepository;
    RecruitmentMapper recruitmentMapper;
    public  List<RecruitmentResponse> getAll(){
        return recruitmentRepository.findAll().stream().map(
                recruitment -> recruitmentMapper.toRecruitmentResponse(recruitment)
        ).toList();
    }

}
