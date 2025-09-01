package com.example.demo;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentRepositoryCustom;
import com.example.demo.Student.StudentService;
import com.example.demo.Student.studentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SecondApproach {
    @MockitoBean
    private studentRepository repository;

    @Autowired
    private StudentService service;

    @Test
    public void testingAdding(){
        Student student = new Student("paula","paula1234@gmail.com", LocalDate.of(1885,8,7));
        List<Student> stds=List.of(student);
        Mockito.when(this.repository.save(student)).thenReturn(student);
        Student student1 = this.service.addStudent(student);
        assertEquals(student.getEmail(),student1.getEmail());
        Mockito.verify(this.repository).save(student);

    }

//    public List<Student> studentByNAME(String sub) {
//
//        Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
//        List<Student> students = this.stdrep.findByNameContaining(sub, sortByName);
//        if(students.isEmpty()){
//            throw new IllegalStateException("no student was found");
//        }
//        return students;
//    }



}
