package com.example.demo.Student;

import java.util.List;
import java.util.Set;

public interface StudentRepositoryCustom {
    List<Student > findStudentsByEmail(Set<String > emails);
    /// we use set here because we want tno redunduncy.
}
