package com.arturo.tasker.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {
	private Long id;
	private String title;
	private String description;
	private boolean completed;
	private LocalDateTime createdAt;
	private Long userId;
}
