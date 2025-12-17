package com.arturo.tasker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arturo.tasker.dto.AuthRequest;
import com.arturo.tasker.dto.AuthResponse;
import com.arturo.tasker.dto.RegisterRequest;
import com.arturo.tasker.entity.User;
import com.arturo.tasker.service.AuthenticationService;
import com.arturo.tasker.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationService authService;
	private final UserService userService;
	
	@Operation(summary = "Register a new user", description="Inputs: email, password and name")
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
		User savedUser = userService.register(request);
		return ResponseEntity.ok(savedUser);
	}
	
	@Operation(summary = "Log in a user", description="Inputs: email and password")
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

}
