package com.mapper;

import com.dto.request.CompanyRequest;
import com.dto.response.CompanyResponse;
import com.entity.Company;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toCompany(CompanyRequest request);
    @Mapping(target = "id", source = "id")
    CompanyResponse toCompanyResponse(Company company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompany(@MappingTarget Company company, CompanyRequest request);

}
