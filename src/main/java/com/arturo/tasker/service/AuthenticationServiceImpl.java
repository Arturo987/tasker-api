package com.arturo.tasker.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.arturo.tasker.dto.AuthRequest;
import com.arturo.tasker.dto.AuthResponse;
import com.arturo.tasker.entity.User;
import com.arturo.tasker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	public AuthResponse login(AuthRequest request) {
		
		// 1. Search user by email
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found")); 
		
		// 2. Verify password
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid password");
		}
		
		// 3. Generate token
		String token = jwtService.generateToken(user);
		
		return new AuthResponse(token);
		
	}

}
