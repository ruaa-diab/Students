package com.example.demo.Student;

import java.util.List;
import java.util.Set;

public interface NameRepositoryCustom {

    List<Student> findNamePaterns(Set<String> names);
}
