package com.arturo.tasker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("h2") // Only whne the active profile is h2
public class SecurityH2Config {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable()) //Deactivates CSRF(Cross-Site Request Forgery)
			.headers(headers -> headers.frameOptions(frame -> frame.disable())) //Allows iframes
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/h2-console*//").permitAll() //Allows total permision to the console
					.anyRequest().permitAll() //So it doesn't ask for logins
				);
			return http.build();
	}
}
