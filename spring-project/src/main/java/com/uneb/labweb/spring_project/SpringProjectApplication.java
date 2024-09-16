package com.uneb.labweb.spring_project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.uneb.labweb.spring_project.model.Course;
import com.uneb.labweb.spring_project.repository.CourseRepository;

@SpringBootApplication
public class SpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
    }
    
    @Bean
    CommandLineRunner initDatabase(CourseRepository courseRepository) {
        return args -> {
            Course course1 = new Course();
            course1.setName("Curso Angular");
            course1.setCategory("Front-end");
            courseRepository.save(course1);

            Course course2 = new Course();
            course2.setName("Curso React");
            course2.setCategory("Front-end");
            courseRepository.save(course2);

            Course course3 = new Course();
            course3.setName("Curso Spring Boot");
            course3.setCategory("Back-end");
            courseRepository.save(course3);
        };
    }
}
