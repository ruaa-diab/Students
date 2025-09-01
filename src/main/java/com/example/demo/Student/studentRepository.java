package com.example.demo.Student;

import jakarta.persistence.QueryHint;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface studentRepository extends JpaRepository<Student,Long> ,StudentRepositoryCustom,NameRepositoryCustom{
    // I AM storing the type wit the type of the primary key.


//select * from student where email=? or u can put @query
@QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
Optional<Student> findStudentByEmail(String email);


    @Query(value="SELECT * FROM student s WHERE s.dop >:date",nativeQuery = true)
    List<Student> findAfterAge(@Param("date") LocalDate ld);

    // Repository
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:sub%")
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<Student> findByNameContaining(@Param("sub") String sub, Sort sort);


    //Find all students sorted by name length (shortest first) - return List<Student>

    @Query("SELECT s FROM Student s ")
    List<Student> findAll(Sort sort);


    @Query("SELECT s.name as name,s.id as id  FROM Student s ORDER BY LENGTH(name) ")
    List<studentSummary> findSpical();



    //pagination
    @Query("SELECT s FROM Student s ORDER BY s.id")
    Page<Student> findAllStudentsWithPagination(Pageable blbl);


    //pagination with sql
    @Query(value = "SELECT * FROM student s ORDER BY LENGTH(name)",
    countQuery="SELECT count(*) FROM student",
    nativeQuery = true)
    Page<Student > findStudentsWithPagination2( Pageable pg);
    //collection parameter
    @Query("SELECT s from Student s WHERE s.name IN:names")
    List<Student> findStudentByNames(@Param("names") List<String> names);



    //collection with pagination
    @Query("SELECT s from Student s WHERE LOWER(s.name) IN :names")
    public Page<Student> pgCollection(@Param("names") List<String> names,Pageable pg);



    //modifying query1
    @Modifying
    @Query("UPDATE Student s SET s.name = CONCAT('Student-', s.name) " )
    void updateNames();  // returns num


}
