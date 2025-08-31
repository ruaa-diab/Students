package com.example.demo.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StudentRepositoryCustomImpl implements
        StudentRepositoryCustom{


    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public List<Student> findStudentsByEmail(Set<String> emails) {

        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query =cb.createQuery(Student.class);
        Root<Student> student=query.from(Student.class);

        Path<String> emailPath=student.get("email");
        List<Predicate> predicates=new ArrayList<>();
        for(String email:emails){
            predicates.add(cb.like(emailPath,"%"+ email+"%"));
        }


        query.select(student).where(
                cb.or(predicates.toArray(new Predicate[predicates.size()])));

                return entityManager.createQuery(query).getResultList();

    }
}
