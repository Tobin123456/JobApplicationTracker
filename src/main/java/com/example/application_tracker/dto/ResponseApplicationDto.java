package com.example.application_tracker.dto;

import jakarta.validation.constraints.NotBlank;

public class ResponseApplicationDto {

    @NotBlank
    private final String jobTitle;

    @NotBlank
    private final String status;

    @NotBlank
    private final String companyName;

    private final String jobUrl;

    public ResponseApplicationDto(String jobTitle, String companyName, String status, String jobUrl) {
        this.jobTitle = jobTitle;
        this.status = status;
        this.companyName = companyName;
        this.jobUrl = jobUrl;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getStatus() {
        return status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobUrl() {
        return jobUrl;
    }
}
