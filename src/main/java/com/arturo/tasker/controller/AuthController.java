package com.arturo.tasker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arturo.tasker.dto.AuthRequest;
import com.arturo.tasker.dto.AuthResponse;
import com.arturo.tasker.dto.RegisterRequest;
import com.arturo.tasker.dto.UserResponse;
import com.arturo.tasker.entity.User;
import com.arturo.tasker.service.AuthenticationService;
import com.arturo.tasker.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationService authService;
	private final UserService userService;
	
	@Operation(summary = "Register a new user", description="Inputs: email, password and name")
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
		User savedUser = userService.register(request);
		
		UserResponse response = UserResponse.builder()
				.id(savedUser.getId())
				.email(savedUser.getEmail())
				.name(savedUser.getName())
				.build();
		
		return ResponseEntity.ok(response);
	}
	
	@Operation(summary = "Log in a user", description="Inputs: email and password. Returns JWT token")
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

}
