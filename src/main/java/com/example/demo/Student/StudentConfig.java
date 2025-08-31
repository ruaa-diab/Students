package com.example.demo.Student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(studentRepository repository){

        return args -> {
            List<Student> students = new ArrayList<>();

            String[] names = {
                    "Mariam", "Alex", "John", "Emma", "Olivia", "Liam", "Noah", "Sophia", "James", "Isabella",
                    "Ethan", "Ava", "Lucas", "Mia", "Mason", "Charlotte", "Logan", "Amelia", "Elijah", "Harper",
                    "Benjamin", "Evelyn", "Jacob", "Abigail", "Michael", "Emily", "Alexander", "Ella", "Daniel", "Scarlett",
                    "Matthew", "Victoria", "Henry", "Grace", "Jackson", "Chloe", "Sebastian", "Lily", "David", "Zoey",
                    "Samuel", "Hannah", "Carter", "Aria", "Owen", "Layla", "Wyatt", "Nora", "Gabriel", "Riley"
            };

            for (int i = 0; i < 50; i++) {
                String name = names[i];
                String email = name.toLowerCase() + i + "@gmail.com";
                LocalDate dob = LocalDate.of(2000 + (i % 5), Month.JANUARY.plus(i % 12), (i % 28) + 1);
                students.add(new Student(name, email, dob));
            }

            repository.saveAll(students);
        };








    };
}
