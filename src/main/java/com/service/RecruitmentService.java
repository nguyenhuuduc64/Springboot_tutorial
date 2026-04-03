package com.service;

import com.dto.request.RecruitmentRequest;
import com.dto.response.CompanyResponse;
import com.dto.response.RecruitmentResponse;
import com.entity.Company;
import com.entity.JobCategory;
import com.entity.Recruitment;
import com.entity.User;
import com.exception.AppException;
import com.exception.ErrorCode;
import com.mapper.CompanyMapper;
import com.mapper.RecruitmentMapper;
import com.repository.CompanyRepository;
import com.repository.JobCategoryRepository;
import com.repository.RecruitmentRepository;
import com.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RecruitmentService {
    RecruitmentRepository recruitmentRepository;
    RecruitmentMapper recruitmentMapper;
    UserRepository userRepository;
    CompanyRepository companyRepository;
    JobCategoryRepository jobCategoryRepository;
    CompanyMapper  companyMapper;
    public  List<RecruitmentResponse> getAll(){
        return recruitmentRepository.findAll().stream().map(
                recruitmentMapper::toRecruitmentResponse
        ).toList();
    }

    public Page<Recruitment> getAllRecruitments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        return recruitmentRepository.findAll(pageable);
    }

    public  RecruitmentResponse getRecruitment(String id){
        Recruitment recruitment = recruitmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RECRUITMENT_NOT_FOUND));
        return recruitmentMapper.toRecruitmentResponse(recruitment);
    }

    public  List<RecruitmentResponse> getRecruitmentByCompanyId(String companyId){
        List<RecruitmentResponse> recruitments = recruitmentRepository.findByCompanyId(companyId)
                .stream().map(recruitment -> recruitmentMapper.toRecruitmentResponse(recruitment))
                .toList();
        return recruitments;
    }

    public RecruitmentResponse create(RecruitmentRequest request){

        log.info("recruitment request: {}", request);

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Company company = companyRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));

        JobCategory jobCategory = jobCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));


        Recruitment recruitment = recruitmentMapper.toRecruitment(request);
        log.info("entity chuan bi luu: {}", recruitment);
        if (request.getRequirements() != null) {
            List<String> cleanRequirements = request.getRequirements().stream()
                    .filter(Objects::nonNull) // Loại bỏ phần tử null thưa ông chủ
                    .map(String::trim)        // Xóa khoảng trắng thừa
                    .filter(s -> !s.isEmpty()) // Loại bỏ chuỗi rỗng
                    .toList();
            recruitment.setRequirements(cleanRequirements);
        }

        recruitment.setCompany(company);
        recruitment.setCategory(jobCategory);

        log.info("thong tin tuyen dung: {}" , recruitment);
        Recruitment response = recruitmentRepository.save(recruitment);
        RecruitmentResponse recruitmentResponse = recruitmentMapper.toRecruitmentResponse(response);
        recruitmentResponse.setCompany(companyMapper.toCompanyResponse(recruitment.getCompany()));
        return recruitmentResponse;
    }

    public String delete(String recruitmentId){
        recruitmentRepository.deleteById(recruitmentId);
        return "recruitment has been deleted";
    }

}
