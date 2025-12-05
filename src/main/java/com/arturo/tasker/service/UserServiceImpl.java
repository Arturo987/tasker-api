package com.arturo.tasker.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arturo.tasker.dto.RegisterRequest;
import com.arturo.tasker.entity.User;
import com.arturo.tasker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final PasswordEncoder passwordEncoder;

		@Override
		public User register(RegisterRequest request) {
		if(userRepository.findByEmail(request.getEmail()).isPresent())  {
			throw new RuntimeException("Email already created");
		}
		
		User user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.name(request.getName())
				.build();
		
		return userRepository.save(user);
	}
	private final UserRepository userRepository;

	public User register(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

}
