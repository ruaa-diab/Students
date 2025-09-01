package com.example.demo;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentController;
import com.example.demo.Student.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


// MockMvc request builders and result matchers
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Hamcrest matchers for JSON assertions
import static org.hamcrest.Matchers.*;

// Mockito BDD style
import static org.mockito.BDDMockito.given;

// Java collections and time
import java.util.Arrays;
import java.time.LocalDate;

// HTTP Media Types (if needed)
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Arrays;

import static org.awaitility.Awaitility.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetting() throws Exception {
        given(studentService.getStudent()).willReturn(
                Arrays.asList(
                        new Student("ruaa",
                                "ruaa1234@gmail.com", LocalDate.of(2002, 9, 7)),
                        new Student("kamet", "kamel1234@gmial.com", LocalDate.of(1665, 8, 4))
                )
        );
        this.mockMvc.perform(get("/api/v1/student"))
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].name").value("ruaa"));
    }


    @Test
    public void testAdding() throws Exception {
        // Just create ONE student object
        Student student = new Student("mhmh", "mhmh@gmail.com", LocalDate.of(2000, 8, 4));

        // Mock the service to return the same student
        given(studentService.addStudent(student)).willReturn(student);

        // Test
        this.mockMvc.perform(post("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());
    }
}

