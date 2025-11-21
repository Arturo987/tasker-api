package com.arturo.tasker.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arturo.tasker.entity.User;
import com.arturo.tasker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
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
