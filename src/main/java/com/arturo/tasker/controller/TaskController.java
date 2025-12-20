package com.arturo.tasker.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.arturo.tasker.dto.TaskRequest;
import com.arturo.tasker.dto.TaskResponse;
import com.arturo.tasker.entity.Task;
import com.arturo.tasker.entity.User;
import com.arturo.tasker.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;
		
	@Operation(summary = "Access to personal tasks")
	@GetMapping
    public List<TaskResponse> getMyTasks() {
		
		User currentUser = getCurrentUser();
		
        return taskService.getAllTasksByUser(currentUser.getId());
    }
	
	@Operation(summary = "Access to a certain personal task")
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponse> getMyTask(@PathVariable Long id) {
				
			User currentUser = getCurrentUser();
			
			Task task = taskService.findById(id)
					.orElseThrow(() -> new RuntimeException("Task not found"));
			
			// The task must belong to the current user
			if (!task.getUser().getId().equals(currentUser.getId())) {
			    throw new RuntimeException("Forbidden");
			}
			
			TaskResponse response = toResponse(task);
			
			return ResponseEntity.ok(response);
			
			}
	
	@Operation(summary = "Update one of your tasks")
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponse> updateTask(
			@Valid
			@PathVariable Long id,
			@RequestBody TaskRequest request
		) {
        User currentUser = getCurrentUser();

        Task existing = taskService.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // The task must belong to the current user
		if (!existing.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Forbidden");
        }
				
		existing.setTitle(request.getTitle());
		existing.setDescription(request.getDescription());
		existing.setCompleted(request.isCompleted());
		
		Task updated = taskService.update(existing);
		
		return ResponseEntity.ok(toResponse(updated));
	}
	
	@Operation(summary = "Delete one of your tasks")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		User currentUser = getCurrentUser();
		Task existing = taskService.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // The task must belong to the current user
		if (!existing.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Forbidden");
        }
		taskService.deleteById(id);
		return ResponseEntity.noContent().build();
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
	
	@Operation(summary = "Create a task")
	@PostMapping
	public TaskResponse create(@Valid @RequestBody TaskRequest request) {
		
		User currentUser = getCurrentUser();
		
		return taskService.createTask(request, currentUser.getId());
	}
	
	private User getCurrentUser() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
	        throw new RuntimeException("Not authenticated");
	    }
	    return (User) auth.getPrincipal();
	}

}
