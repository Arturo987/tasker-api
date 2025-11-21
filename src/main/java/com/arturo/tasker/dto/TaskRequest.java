package com.arturo.tasker.dto;

import lombok.Data;

@Data
public class TaskRequest {
	private Long userId;
	private String title;
	private String description;
	private boolean completed;
}
