package com.arturo.tasker.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.arturo.tasker.entity.User;
import com.arturo.tasker.repository.UserRepository;
import com.arturo.tasker.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		// 1. Take a look at header Authorization
		final String authHeader = request.getHeader("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			// If there is no token, we follow the usual chain
			filterChain.doFilter(request, response);
			return;
		}
		
		// 2. We take the token
		final String token = authHeader.substring(7);
		
		// 3. We take the username (email)
		final String username = jwtService.extractUsername(token);
		
		// 4. If we have the username and there is not yet authentication in the context
        if (username != null) {

			User user = userRepository.findByEmail(username).orElse(null);
			if (user != null) {
				// Here we could set roles if we had them
				var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
				
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(
								user,
								null,
								authorities
							);
				
				authToken.setDetails(
							new WebAuthenticationDetailsSource().buildDetails(request)
						);
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
	}
        // 5. Continue the chain
        filterChain.doFilter(request, response);
	}
}
