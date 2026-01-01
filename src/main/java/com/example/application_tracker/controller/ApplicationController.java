package com.example.application_tracker.controller;

import com.example.application_tracker.domain.Application;
import com.example.application_tracker.domain.Company;
import com.example.application_tracker.domain.Job;
import com.example.application_tracker.dto.CreateApplicationDto;
import com.example.application_tracker.dto.ResponseApplicationDto;
import com.example.application_tracker.repository.ApplicationRepository;
import com.example.application_tracker.repository.CompanyRepository;
import com.example.application_tracker.repository.JobRepository;
import com.example.application_tracker.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<ResponseApplicationDto> getAll() {
        return applicationService.getAllApplications();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseApplicationDto create(@Valid @RequestBody CreateApplicationDto dto) {
        return applicationService.createApplication(dto);
    }
}
