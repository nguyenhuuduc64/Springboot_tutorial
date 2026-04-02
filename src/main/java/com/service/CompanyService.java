package com.service;

import com.dto.request.CompanyRequest;
import com.dto.response.CompanyResponse;
import com.entity.Company;
import com.entity.User;
import com.exception.AppException;
import com.exception.ErrorCode;
import com.mapper.CompanyMapper;
import com.repository.CompanyRepository;
import com.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyService {
    CompanyMapper companyMapper;
    CompanyRepository companyRepository;
    UserRepository userRepository;
    @Transactional
    public CompanyResponse createCompany(String userId, CompanyRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Company company = companyMapper.toCompany(request);
        company.setUser(user);
        Company savedCompany = companyRepository.save(company);
        return companyMapper.toCompanyResponse(savedCompany);
    }

    public CompanyResponse edit(CompanyRequest request, String id){
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
        log.info("thong tin company get duoc {}", company);
        companyMapper.updateCompany(company, request);

        Company response = companyRepository.save(company);
        return companyMapper.toCompanyResponse(response);

    }

    public CompanyResponse getCompanyByUserId(String userId){
        // Tìm trực tiếp trong bảng Company luôn cho nhanh thưa ông chủ
        Company company = companyRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
        log.info("day la thong tin company {}", companyMapper.toCompanyResponse(company));
        return companyMapper.toCompanyResponse(company);
    }

    public CompanyResponse getCompanyByCompanyId(String companyId){
        // Tìm trực tiếp trong bảng Company luôn cho nhanh thưa ông chủ
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
        return companyMapper.toCompanyResponse(company);
    }


}
