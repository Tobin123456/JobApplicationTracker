package com.example.application_tracker.mapper;

import com.example.application_tracker.domain.Application;
import com.example.application_tracker.dto.ResponseApplicationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(source = "job.title", target = "jobTitle")
    @Mapping(source = "job.company.name", target = "companyName")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "job.url", target = "jobUrl")
    ResponseApplicationDto toResponseDto(Application application);
}
