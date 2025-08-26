package com.example.demo.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class EmployeeController {


   private final EmployeeService employeeService;


   @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
       return this.employeeService.getAllEmployees();
    }


    @PostMapping
    public Employee insert(@RequestBody Employee employee){

       return this.employeeService.addEmployee(employee);
    }

    @GetMapping("/email/{email}")
    public Employee findByEm(@PathVariable  String email){
       return this.employeeService.getEmployeeByEmail(email);
    }

    @DeleteMapping ("/{id}")
    public void delete(@PathVariable String id){
       this.employeeService.deleteEmployee(id);

    }


    @PutMapping
    public ResponseEntity<?>  update(@RequestParam String id,
                                     @RequestParam String first,
                                     @RequestParam String second){

       try{
           this.employeeService.updateName(id,first,second);
           return ResponseEntity.ok("the employee name was updated successfully");

       }catch(Exception c){
           return ResponseEntity.badRequest().body(c.getMessage());
       }

    }

}
