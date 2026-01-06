package com.example.application_tracker.dto;

import jakarta.validation.constraints.NotBlank;

public record ResponseCreateUserDto(@NotBlank String message) {

    public ResponseCreateUserDto(String message) {
        this.message = message;
    }
}
