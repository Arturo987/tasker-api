package com.arturo.tasker.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arturo.tasker.dto.TaskRequest;
import com.arturo.tasker.dto.TaskResponse;
import com.arturo.tasker.entity.Task;
import com.arturo.tasker.entity.User;
import com.arturo.tasker.repository.TaskRepository;
import com.arturo.tasker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;
	
	@Override
	public Task create(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public List<Task> findByUserId(Long userId) {
		return taskRepository.findByUserId(userId);
	}

	@Override
	public Optional<Task> findById(Long id) {
		return taskRepository.findById(id);
	}

	@Override
	public Task update(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public void deleteById(Long id) {
		taskRepository.deleteById(id);
	}
	
	@Override
	public List<TaskResponse> getAllTasksByUser(Long userId) {
		return taskRepository.findByUserId(userId)
			.stream()
			.map(this::toResponse)
			.collect(Collectors.toList());
	}

	private TaskResponse toResponse(Task task) {
	    return TaskResponse.builder()
	            .id(task.getId())
	            .title(task.getTitle())
	            .description(task.getDescription())
	            .completed(task.isCompleted())
				.createdAt(task.getCreatedAt())
	            .userId(task.getUser() != null ? task.getUser().getId() : null)
	            .build();
	}

	@Override
	public TaskResponse createTask(TaskRequest request, Long userId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		Task task = Task.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.user(user)
				.build();
		
		Task saved = taskRepository.save(task);
		
		return TaskResponse.builder()
				.id(saved.getId())
				.title(saved.getTitle())
				.description(saved.getDescription())
				.createdAt(saved.getCreatedAt())
				.userId(saved.getUser() != null ? saved.getUser().getId() : null)
				.build();
	}

}
