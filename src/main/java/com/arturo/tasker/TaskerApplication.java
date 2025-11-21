package com.arturo.tasker;

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

	@Bean
	CommandLineRunner init(UserRepository users, TaskRepository tasks) {
	    return args -> {
	        User u = users.save(
	            User.builder()
	                .email("test@test.com")
	                .password("1234")
	                .name("Arturo")
	                .build()
	        );

	        tasks.save(
	            Task.builder()
	                .title("Probar API")
	                .description("Hacer el CRUD")
	                .user(u)
	                .build()
	        );
	    };
	}

}
