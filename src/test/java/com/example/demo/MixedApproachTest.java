package com.example.demo;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentService;
import com.example.demo.Student.studentRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.support.ScheduledTaskObservationDocumentation;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MixedApproachTest {

    @Nested
    @TestConfiguration
    class TestConfig{
        @Bean
        @Primary
        public StudentService studentService(studentRepository repo){
            return new StudentService(repo);
        }

        @MockitoBean
        private studentRepository repository;

        @Autowired
        private StudentService service;

        @Test
        public void gettingStudent(){
            Student std=new Student("kamal","kamal1234@gmail.com", LocalDate.of(1995,8,7));
            List<Student> fakeList=List.of(std);
            Mockito.when(this.repository.findAll()).thenReturn(fakeList);
            //so like iam telling it when someone callsthis method on you return the list
            List<Student> stds=this.service.getStudent();
            assertEquals(1,stds.size());


        }
        @Test
        public void testingDelete(){

            Long id=1L;
            Student std=new Student("kamalll","kamal12345@gmail.com", LocalDate.of(1995,8,7));
            Mockito.when(repository.existsById(id)).thenReturn(true);


            this.service.delete(id);
            Mockito.verify(this.repository).deleteById(id);



        }


    }




}
