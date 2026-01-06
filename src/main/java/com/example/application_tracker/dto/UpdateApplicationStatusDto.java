package com.example.application_tracker.dto;

import com.example.application_tracker.domain.Application;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record UpdateApplicationStatusDto(@NotNull Long appID, @NotNull Application.Status status) {

    public UpdateApplicationStatusDto(@JsonProperty("appID") Long appID, @JsonProperty("status") Application.Status status) {
        this.appID = appID;
        this.status = status;
    }
}
