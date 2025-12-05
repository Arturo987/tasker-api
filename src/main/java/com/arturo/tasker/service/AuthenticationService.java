package com.arturo.tasker.service;

import com.arturo.tasker.dto.AuthRequest;
import com.arturo.tasker.dto.AuthResponse;

public interface AuthenticationService {
	AuthResponse login(AuthRequest request);
}
