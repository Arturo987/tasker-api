package com.arturo.tasker.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arturo.tasker.dto.TaskResponse;
import com.arturo.tasker.entity.Task;
import com.arturo.tasker.entity.User;
import com.arturo.tasker.repository.TaskRepository;
import com.arturo.tasker.repository.UserRepository;
import com.arturo.tasker.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
	private final TaskService taskService;

    @Operation(summary = "Get all users for an admin", description = "Requires admin role")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Operation(summary = "Get all tasks for an admin", description = "Requires admin role")
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    @Operation(summary = "Deletes a certain task", description = "Requires admin role")
    @DeleteMapping("tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
    	if (taskRepository.existsById(id)) {
    		taskRepository.deleteById(id);
    	}
    }
    
    @Operation(summary = "Deletes a certain user", description = "Requires admin role")
    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable Long id) {
    	// Delete tasks of user to avoid constraints
    	taskRepository.deleteAll(
    			taskRepository.findByUserId(id)
		);
    	
    	if(userRepository.existsById(id)) {
    		userRepository.deleteById(id);
    	}
    }
    

    @Operation(summary = "Obtener tareas de un usuario", description = "Solo accesible para administradores")
    @GetMapping("/tasks/user/{userId}")
    public ResponseEntity<List<TaskResponse>> getTasksByUserId(@PathVariable Long userId) {

        List<Task> tasks = taskService.findByUserId(userId);

        List<TaskResponse> responses = tasks.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
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

}
