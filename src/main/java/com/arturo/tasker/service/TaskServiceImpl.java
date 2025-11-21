package com.arturo.tasker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arturo.tasker.entity.Task;
import com.arturo.tasker.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	
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

}
