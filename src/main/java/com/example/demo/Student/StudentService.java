package com.example.demo.Student;

import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service

//servise means that here i am in the business logic areaaaaaaa



//so autowired knows to make instance out of thisssssssss
public class StudentService {
    private final studentRepository stdrep;


    @Autowired
    public StudentService(studentRepository stdrep) {
        this.stdrep = stdrep;
    }



    @Cacheable(value="studentsCache")
    public List<Student> getStudent(){
        return stdrep.findAll();
    }


    @CacheEvict(value  ="StudentsCache, allEntries = true")
    public void addStudent(Student std) {
        System.out.println(std);
        Optional<Student> studentByEmail=
                this.stdrep.findStudentByEmail(std.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("email is taken");
        }
        this.stdrep.save(std);

    }

    @CacheEvict(value  ="StudentsCache, allEntries = true")

    public void delete(Long studentId) {


            boolean exists=this.stdrep.existsById(studentId);
          if(exists){
              this.stdrep.deleteById(studentId);
            System.out.println("student deleted successfully");


        }else{
            throw new NullPointerException("student not found");
        }


    }


    @Transactional
    @CacheEvict(value  ="StudentsCache, allEntries = true")

    public void update(Long studentId,String name,String email) {
        Student std=this.stdrep.findById(studentId).orElseThrow(()->
                new IllegalStateException("this is has no student "));

        if(name!=null&&name.length()>0&&
        !Objects.equals(name,std.getName())){
            std.setName(name);
        }
        if(email!=null&&email.length()>0&&!Objects.equals(email,std.getEmail())){
            std.setEmail(email);
        }




    }


    public List<Student> getStudentsAfterDate(LocalDate date) {

        List<Student> stds=this.stdrep.findAfterAge(date);
        if(stds.isEmpty()||stds==null){
            throw new IllegalStateException("no student was found");
        }
        return stds;
    }


       public List<Student> studentByNAME(String sub) {

           Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
           List<Student> students = this.stdrep.findByNameContaining(sub, sortByName);
           if(students.isEmpty()){
               throw new IllegalStateException("no student was found");
           }
           return students;
       }

    public List<Student> sortByName() {
        Sort sbn=Sort.by(Sort.Direction.DESC,"name");
        List<Student> sorted=this.stdrep.findAll(sbn);
        return sorted;
    }



    public List<Student> sortByLength(){
        return this.stdrep.findAll(JpaSort.unsafe("Length(name)DESC"));
    }




    public List<studentSummary> findAfterAge() {
        List<studentSummary> students = stdrep.findSpical();

        if (students.isEmpty()) {
            throw new IllegalStateException("no student was found");

        }
        return students;
    }


    public Page<Student> fetching(){

        Pageable pg=PageRequest.of(0,11);

        Page<Student> page =this.stdrep.findAllStudentsWithPagination(pg);
        if(page.isEmpty()){
            throw new IllegalStateException("no students were found");
        }
        return page;



    }


    public Page<Student > fetching2(int num,int num2){
        Page<Student> page=
                this.stdrep.findStudentsWithPagination2
                        (PageRequest.of(num,num2));
        if (page.isEmpty()){
            throw new IllegalStateException("no pages to fetch");
        }
        return page;
    }


    public List<Student> collectionExample(List<String> names){
        List<Student> result=this.stdrep.findStudentByNames(names);
        if(result.isEmpty()){
            throw new IllegalStateException("there are no students to fetch");

        }
        return result;

    }



    public Page<Student > candp(int pSize,int lines,List<String> names){

            Pageable pg = PageRequest.of(pSize, lines);
            Page<Student> result = this.stdrep.pgCollection(names,pg);
     if(result.isEmpty()){
         throw new IllegalStateException("no students to fetch");
     }
     return result;

    }

    @Transactional
    public void updateNames(){
        this.stdrep.updateNames();
    }

    public List<Student >  findByEMAIL(Set<String> patterns){
        return this.stdrep.findStudentsByEmail(patterns);
    }


    public List<Student> findByName(Set<String> names){
        return this.stdrep.findNamePaterns(names);
    }
}


