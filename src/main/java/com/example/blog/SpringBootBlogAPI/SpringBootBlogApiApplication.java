package com.example.blog.SpringBootBlogAPI;

import com.example.blog.SpringBootBlogAPI.entity.Role;
import com.example.blog.SpringBootBlogAPI.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootBlogApiApplication implements CommandLineRunner {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogApiApplication.class, args);
	}

	//	second way for table metadata or auto running scripts
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		if (!roleRepository.existsById(1L)){
			Role adminRole = new Role();
			adminRole.setName("ROLE_ADMIN");
			roleRepository.save(adminRole);

		}

		if (!roleRepository.existsById(2L)) {
			Role userRole = new Role();
			userRole.setName("ROLE_USER");
			roleRepository.save(userRole);

		}

	}
}
