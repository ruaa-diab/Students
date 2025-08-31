package com.example.demo.Student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.RemoteRef;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    //THIS basically will magically initiate studentService
    //and inject it into the constructor
    //thats what we mean by autowired
    public StudentController(StudentService studentService) {

        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudent() {

        return studentService.getStudent();
    }

    @PostMapping
    public void AddNewStudent(@RequestBody Student std) {
        try {
            this.studentService.addStudent(std);


        } catch (Exception m) {
            ResponseEntity.badRequest().body(m.getMessage());

        }

    }


    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") Long studentId) {
        try {
            this.studentService.delete(studentId);
            return ResponseEntity.ok("student deleted successfuly");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<String> updateStudent(@PathVariable("studentId") Long studentId,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String email) {


        try {
            this.studentService.update(studentId, name, email);
            return ResponseEntity.ok("student with " + studentId + " updated successfully");

        } catch (Exception n) {
            return ResponseEntity.badRequest().body(n.getMessage());

        }
    }


    @GetMapping("/after-date")
    public List<Student> getStudentsAfterDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return studentService.getStudentsAfterDate(date);
    }


    @GetMapping("/sub")
    public List<Student> getStudentByNAME(@RequestParam("sub") String sub) {
        return this.studentService.studentByNAME(sub);
    }

    @GetMapping("/students/sorted")
    public List<Student> nameSorted() {
        return this.studentService.sortByName();
    }


    @GetMapping("/length/sort")
    public List<Student> fss() {
        return this.studentService.sortByLength();
    }

    @GetMapping("/AGE")
    public ResponseEntity<?> getStudentsAfterAge() {
        try {
            List<studentSummary> ss = this.studentService.findAfterAge();
            return ResponseEntity.ok(ss);
        } catch (Exception n) {
            return ResponseEntity.badRequest().body(n.getMessage()); // or wrap error in a response DTO
        }
    }

    @GetMapping("/page")
    public ResponseEntity<?> getTheFirstPage() {
        try {
            Page<Student> pg = this.studentService.fetching();
            return ResponseEntity.ok(pg);
        } catch (Exception m) {
            return ResponseEntity.badRequest().body(m.getMessage());

        }


    }

    @GetMapping(path = "{num}/{num2}")
    public ResponseEntity<?> getApage2(@PathVariable("num") int num,
                                       @PathVariable("num2") int num2) {
        try {
            Page<Student> PS = this.studentService.fetching2(num, num2);
            return ResponseEntity.ok(PS);
        } catch (Exception m) {
            return ResponseEntity.badRequest().body(m.getMessage());
        }
    }

    @GetMapping("/names")
    public ResponseEntity<?> collectionExample(@RequestParam("names") List<String> names) {
        try {
            List<Student> students = this.studentService.collectionExample(names);
            return ResponseEntity.ok(students);


        } catch (Exception m) {
            return ResponseEntity.badRequest().body(m.getMessage());
        }
    }

    @GetMapping("/special")
    public ResponseEntity<?> pgWithCollection(@RequestParam("names")List<String> names,
                                              @RequestParam("page")int page,
                                              @RequestParam("size")int size){
        try{
            Page<Student > pf=this.studentService.candp(page,size,names);
            return ResponseEntity.ok(Map.of(
                    "students", pf.getContent(),
                    "page", page,
                    "size", size,
                    "totalPages", pf.getTotalPages(),
                    "totalElements", pf.getTotalElements()
            ));

        }catch(Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/update-names")
    public void up(){
        this.studentService.updateNames();
    }

    @GetMapping("/haha")
    public List<Student> custom(@RequestParam List<String >emails){
        Set<String > emailSet=new HashSet<>(emails);
        return this.studentService.findByEMAIL(emailSet);
    }


    @GetMapping("/hehe")
    public List<Student> CUSTOM2(@RequestParam List<String > names){
        Set<String> namesSet=new HashSet<>(names);
        return this.studentService.findByName(namesSet);
    }
}

