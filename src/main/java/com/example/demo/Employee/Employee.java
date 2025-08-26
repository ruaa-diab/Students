package com.example.demo.Employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Employee")
public class Employee {


    @Id
    private String id;


    @NotBlank
    @Size(max=100)
    private String firstName;
    private String lastName;

    @NotBlank
    @Size(max=100)
    @Indexed(unique = true)
    private String emailId;


    public Employee() {
    }

    public Employee(String id, String firstName, String lastName, String emailId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
    }


    public Employee( String firstName,String lastName, String emailId) {
        this.lastName = lastName;

        this.firstName = firstName;
        this.emailId = emailId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }




}
