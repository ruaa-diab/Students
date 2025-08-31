package com.example.demo;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentRepositoryCustom;
import com.example.demo.Student.studentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = DemoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class StudentControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private studentRepository   studentRep;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        this.studentRep.deleteAll();
    }

    //fakeeee data.
    private Student creatTestStudent(String name, String email, LocalDate dob){
        Student student=new Student(name,email,dob);
        return this.studentRep.save(student);


    }
    @Test
    public void gettingStudents() throws Exception {
        this.creatTestStudent("ruaa","ruaadiab2002@gmail.com",LocalDate.of(2002,9,7));
        this.mockMvc.perform(get("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("ruaa"))
                .andExpect(jsonPath("$[0].email").value("ruaadiab2002@gmail.com"))
                .andExpect(jsonPath("$[0].dop").value("2002-09-07"))
                .andExpect(jsonPath("$[0].age").value(22));

    }


    @Test
    public void addingStudent() throws Exception {
        Student student = new Student("rose", "diabroa@gmail.com", LocalDate.of(2002, 9, 7));
        String jsonBody = this.objectMapper.writeValueAsString(student);
        this.mockMvc.perform(post("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("rose")))
                .andExpect(jsonPath("$.email", is("diabroa@gmail.com")))
                .andExpect(jsonPath("$.dop", is("2002-09-07")));
    }





    }
