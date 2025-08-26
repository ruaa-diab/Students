package com.example.demo.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private  final EmployeeRepository employeeRep;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRep) {
        this.employeeRep = employeeRep;
    }


    public List<Employee> getAllEmployees(){
        return employeeRep.findAll();
    }


    public Employee addEmployee(Employee employee){
        return this.employeeRep.save(employee);
    }

    public Employee getEmployeeByEmail(String email){
        return this.employeeRep.findByEmailId(email);
    }


    public void deleteEmployee(String id){
        this.employeeRep.deleteById((id));
    }



    public void updateName(String id,String name1,String name2){
       Employee employee = this.employeeRep.findById(id).orElseThrow(
               ()->new IllegalStateException("this id has no employee"));


            employee.setFirstName(name1);
            employee.setLastName(name2);
            this.employeeRep.save(employee);



    }



}
