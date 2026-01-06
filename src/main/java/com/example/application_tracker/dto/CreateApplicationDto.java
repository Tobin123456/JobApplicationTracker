package com.example.application_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateApplicationDto(@NotBlank String jobTitle, @NotBlank String companyName, @URL String url) {

    @JsonCreator
    public CreateApplicationDto(
            @JsonProperty("jobTitle") String jobTitle,
            @JsonProperty("companyName") String companyName,
            @JsonProperty("url") String url
    ) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.url = url;
    }

    @Override
    public String jobTitle() {
        return jobTitle;
    }

    @Override
    public String companyName() {
        return companyName;
    }

    @Override
    public String url() {
        return url;
    }
}
