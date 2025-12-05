package com.arturo.tasker.service;

import java.util.Optional;

import com.arturo.tasker.dto.RegisterRequest;
import com.arturo.tasker.entity.User;

public interface UserService {

	User register(User user);
	User register(RegisterRequest request);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findById(Long id);
}
