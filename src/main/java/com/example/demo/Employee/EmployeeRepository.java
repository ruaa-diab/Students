package com.example.demo.Employee;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee,String> {

Employee findByEmailId (String emailID);


Optional<Employee> findById(String id);


}
