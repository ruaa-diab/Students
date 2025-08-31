package com.example.demo;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentRepositoryCustom;
import com.example.demo.Student.studentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = DemoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class StudentControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private studentRepository   studentRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        this.studentRepository.deleteAll();
    }

    //fakeeee data.
    private Student creatTestStudent(String name, String email, LocalDate dop){
        Student student=new Student(name,email,dop);
        return this.studentRepository.save(student);


    }
    @Test
    public void gettingStudents() throws Exception {
        this.creatTestStudent("ruaa","ruaadiab2002@gmail.com",LocalDate.of(2002,9,7));
        this.mockMvc.perform(get("api/v1/student")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect()

    }



}
