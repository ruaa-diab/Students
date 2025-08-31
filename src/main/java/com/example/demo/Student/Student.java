package com.example.demo.Student;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@Table
public class Student {
    @Id
    @SequenceGenerator(
            name="student_sequence",
    sequenceName="student_sequence",
    allocationSize=1)
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator="student_sequence"
    )

    private long id;
    private String name;
    private String email;
    private LocalDate dop;

    @Transient
    private Integer age;

    public Student() {
    }

    public Student( long id,
                   LocalDate dop,
                   String email,

                   String name) {
        this.dop = dop;
        this.email = email;
        this.id = id;
        this.name = name;
    }


    public Student(String name,
                   String email,
                   LocalDate dop
                 ) {
        this.name = name;
        this.email = email;
        this.dop = dop;
    }

    public Integer getAge() {
        return Period.between(this.dop,LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDop() {
        return dop;
    }

    public void setDop(LocalDate dop) {
        this.dop = dop;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dop=" + dop +
                '}';
    }
}
/*
PATCH /api/posts/45 HTTP/1.1
Host:exampleapi.com
Content-Type:application/json
Authorization:Bearer ruafgh234
Accept:application/json

{
"Comment":"Wild day"
}





HTTP/1.1 200 OK
Content-Type:application/json

{
"data":"Post was updated"
}

 */