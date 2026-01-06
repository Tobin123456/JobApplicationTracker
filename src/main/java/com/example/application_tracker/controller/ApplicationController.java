package com.example.application_tracker.controller;


import com.example.application_tracker.dto.CreateApplicationDto;
import com.example.application_tracker.dto.ResponseApplicationDto;
import com.example.application_tracker.dto.UpdateApplicationStatusDto;
import com.example.application_tracker.service.ApplicationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    @SecurityRequirement(name = "basicAuth")
    public List<ResponseApplicationDto> getAll() {
        return applicationService.getAllApplications();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    @SecurityRequirement(name = "basicAuth")
    public ResponseApplicationDto create(@Valid @RequestBody CreateApplicationDto dto) {
        return applicationService.createApplication(dto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    @SecurityRequirement(name = "basicAuth")
    public void updateStatus(@Valid @RequestBody UpdateApplicationStatusDto dto) {
        applicationService.updateApplicationStatus(dto);
    }

}
