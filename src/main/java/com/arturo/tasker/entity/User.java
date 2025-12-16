package com.arturo.tasker.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	public enum Role {
		USER,
		ADMIN
	}
	
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;
	
}
