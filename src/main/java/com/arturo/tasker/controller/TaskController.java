package com.arturo.tasker.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.arturo.tasker.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;
	private final UserService userService;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	
	@Operation(summary = "Access to personal tasks")
	@GetMapping
    public List<TaskResponse> getMyTasks() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("AUTH TYPE: " + auth.getClass());
		System.out.println("PRINCIPAL: " + auth.getPrincipal());

		if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
		    throw new RuntimeException("Not authenticated");
		}

		User currentUser = (User) auth.getPrincipal();
		
        return taskService.getAllTasksByUser(currentUser.getId());
    }
	
	@Operation(summary = "Access to a certain personal task")
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponse> getMyTask(@PathVariable Long id) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
			if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
				throw new RuntimeException("Not authenticated");
			}
						
			User currentUser = (User) auth.getPrincipal();
			
			Task task = taskService.findById(id)
					.orElseThrow(() -> new RuntimeException("Task not found"));
			
			// The task must belong to the current user
			if (!task.getUser().getId().equals(currentUser.getId())) {
			    throw new RuntimeException("Forbidden");
			}
			
			TaskResponse response = toResponse(task);
			
			return ResponseEntity.ok(response);
			
			}
	
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponse> updateTask(
			@PathVariable Long id,
			@RequestBody TaskRequest request
		) {
		Task existing = taskService.findById(id)
				.orElseThrow(() -> new RuntimeException("Task not found"));
		
		existing.setTitle(request.getTitle());
		existing.setDescription(request.getDescription());
		existing.setCompleted(request.isCompleted());
		
		Task updated = taskService.update(existing);
		
		return ResponseEntity.ok(toResponse(updated));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
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
	
	@PostMapping
	public TaskResponse create(@RequestBody TaskRequest request) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User currentUser = (User) auth.getPrincipal();
		
		return taskService.createTask(request, currentUser.getId());
	}
}
