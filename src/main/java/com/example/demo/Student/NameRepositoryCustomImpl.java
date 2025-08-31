package com.example.demo.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NameRepositoryCustomImpl implements
        NameRepositoryCustom{


    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Student> findNamePaterns(Set<String> names) {
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<Student> query=cb.createQuery(Student.class);
        Root<Student> student= query.from(Student.class);

        Path<String> namePath=student.get("name");

        List<Predicate> ps=new ArrayList<>();
        for(String name:names){
            ps.add(cb.like(namePath,"%"+name+"%"));

        }

        query.select(student).where(
                cb.or(ps.toArray(new Predicate[ps.size()]))
        );

        return em.createQuery(query).getResultList();
    }
}
