package com.example.application_tracker.dto;

import com.example.application_tracker.domain.Application;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResponseApplicationDto(@NotNull Long appID, @NotBlank String jobTitle, @NotBlank String companyName, @NotNull Application.Status status,
                                     String jobUrl) {

    public ResponseApplicationDto(Long appID, String jobTitle, String companyName, Application.Status status, String jobUrl) {
        this.appID = appID;
        this.jobTitle = jobTitle;
        this.status = status;
        this.companyName = companyName;
        this.jobUrl = jobUrl;
    }
}
