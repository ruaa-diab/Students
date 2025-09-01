package com.example.demo;

import com.example.demo.Student.Student;
import com.example.demo.Student.studentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private studentRepository repository;



    @Test
    public void findByEmailTesting(){
        Student s1=new Student("ruaa","ruaadiab2002@gmail.com", LocalDate.of(1996,8,5));
        Student s2=new Student("meme","meme1234@gmail.com",LocalDate.of(1987,8,7));
        this.entityManager.persist(s1);
        this.entityManager.persist(s2);
        this.entityManager.flush();
        List<Student>std=this.repository.findAfterAge(LocalDate.of(1995,5,7));

        assertEquals(1,std.size());

    }

    @Test
    public void containTesting(){
        Student s1=new Student("ruaa","ruaadiab2002@gmail.com", LocalDate.of(1996,8,5));
        Student s2=new Student("merume","meme1234@gmail.com",LocalDate.of(1987,8,7));
        this.entityManager.persist(s1);
        this.entityManager.persist(s2);
        this.entityManager.flush();
       List<Student> stds= this.repository.findByNameContaining("ru", Sort.by("name"));
       assertEquals(2,stds.size());
       assertEquals("merume",stds.get(0).getName());



    }



}
