package com.example.application_tracker.controller;


import com.example.application_tracker.dto.CreateUserDto;
import com.example.application_tracker.dto.ResponseCreateUserDto;
import com.example.application_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCreateUserDto registerUser(@RequestBody CreateUserDto createUserDto) {
        return userService.createApplication(createUserDto);
    }
}
