package com.arturo.tasker.service;

import java.util.List;
import java.util.Optional;

import com.arturo.tasker.dto.TaskRequest;
import com.arturo.tasker.dto.TaskResponse;
import com.arturo.tasker.entity.Task;

public interface TaskService {
	
	Task create(Task task);
	
	List<Task> findByUserId(Long userId);
	
	Optional<Task> findById(Long id);
	
	Task update(Task task);
	
	void deleteById(Long id);
	
	TaskResponse createTask(TaskRequest request, Long userId);
	
	List<TaskResponse> getAllTasksByUser(Long userId);
}
