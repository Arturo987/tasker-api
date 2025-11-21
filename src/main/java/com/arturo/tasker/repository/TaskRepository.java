package com.arturo.tasker.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arturo.tasker.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUserId(Long userId);
}
