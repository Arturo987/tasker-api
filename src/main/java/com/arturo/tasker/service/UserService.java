package com.arturo.tasker.service;

import java.util.Optional;

import com.arturo.tasker.entity.User;

public interface UserService {

	User register(User user);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findById(Long id);
}
