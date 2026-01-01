package com.example.application_tracker.service;

import com.example.application_tracker.domain.Application;
import com.example.application_tracker.domain.Company;
import com.example.application_tracker.domain.Job;
import com.example.application_tracker.dto.CreateApplicationDto;
import com.example.application_tracker.dto.ResponseApplicationDto;
import com.example.application_tracker.repository.ApplicationRepository;
import com.example.application_tracker.repository.CompanyRepository;
import com.example.application_tracker.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationService {

    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(CompanyRepository companyRepository, JobRepository jobRepository,
                              ApplicationRepository applicationRepository) {
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    @Transactional
    public ResponseApplicationDto createApplication(CreateApplicationDto dto) {
        Company company = new Company(dto.getCompanyName());
        companyRepository.save(company);

        Job job = new Job(dto.getJobTitle(), company, dto.getUrl());
        jobRepository.save(job);

        Application application = new Application(job);
        applicationRepository.save(application);

        return new ResponseApplicationDto(job.getTitle(), company.getName(), application.getStatus().name());
    }

    public List<ResponseApplicationDto> getAllApplications() {
        return applicationRepository.findAll()
                .stream()
                .map(a -> new ResponseApplicationDto(a.getJob().getTitle(), a.getJob().getCompany().getName(), a.getStatus().name()))
                .toList();
    }
}
