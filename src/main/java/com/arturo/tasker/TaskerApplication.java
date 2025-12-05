package com.arturo.tasker;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.arturo.tasker.entity.Task;
import com.arturo.tasker.entity.User;
import com.arturo.tasker.repository.TaskRepository;
import com.arturo.tasker.repository.UserRepository;

@SpringBootApplication
public class TaskerApplication {

    private final UserRepository userRepository;

    TaskerApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(TaskerApplication.class, args);
	}

}
