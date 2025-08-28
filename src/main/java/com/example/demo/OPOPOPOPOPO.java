package com.example.demo;

/*
1. @NotBlank means that this string can tbe null
2. @Size(max=100)   this means that this strung at most be 100
3. for mongo:

           0 :spring.data.mongodb.uri=mongodb://localhost:27017/employeeDB
           0.1:on the class we have to make it collection so we write (
           @document(collection:Employee)
           1.@Indexed(unique = true ) this mean that this
            index or entity can happen more than once no duplication.
             2. FOR THE INTERFACE WE MAKE IT AND  then we make it
             extends MongoRepository<nameOfTheClass,type of the primary key (id)

chache remmmm:

    /*
    step 1 :
    <dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-jcache</artifactId>
    <version>6.5.2.Final</version>
</dependency>

<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>3.10.8</version>
    <classifier>jakarta</classifier>
</dependency>


Step 2: Enable caching in Spring Boot

In your main application class:

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching  // <-- Enables caching globally
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}


Why:
Spring won’t use caches unless @EnableCaching is present.

Step 3: Configure Ehcache

Create a file src/main/resources/ehcache.xml:

<?xml version="1.0" encoding="UTF-8"?>
<config xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'
        xmlns='http://www.ehcache.org/v3'
        xsi:schemaLocation="http://www.ehcache.org/v3 http://www.ehcache.org/schema/ehcache-core-3.0.xsd">

    <cache alias="studentsCache">
        <expiry>
            <ttl unit="minutes">10</ttl> <!-- cache expires after 10 minutes -->
        </expiry>
        <heap unit="entries">1000</heap> <!-- max 1000 student objects -->
    </cache>

</config>


Why:

TTL: controls cache expiration.

Heap entries: prevents memory from exploding.

Alias: studentsCache will be used in your code.



Step 4: Configure Spring Boot to use Ehcache

In application.properties:

spring.cache.jcache.config=classpath:ehcache.xml


Why:

Spring Boot needs to know which cache configuration to use.


Step 5: Annotate methods for caching

In your StudentService:

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

@Service
public class StudentService {

    // Cache this method result
    @Cacheable(value = "studentsCache")
    **************this because it is a list /and this is the name of region factory
    public List<Student> getStudent() {
        return stdrep.findAll();
    }

    // Evict cache on add/delete/update
    @CacheEvict(value = "studentsCache", allEntries = true)


    ************* this annotation to do cache validationnnnn
    so now with every crud op we have to use this so
    when updation db we update cache too*****

    **********amd the allentries=true means we clear it before every methodd****
    public void addStudent(Student std) {
        Optional<Student> studentByEmail = this.stdrep.findStudentByEmail(std.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("email is taken");
        }
        this.stdrep.save(std);
    }

    @CacheEvict(value = "studentsCache", allEntries = true)
    public void delete(Long studentId) {
        boolean exists = this.stdrep.existsById(studentId);
        if(exists){
            this.stdrep.deleteById(studentId);
        } else {
            throw new NullPointerException("student not found");
        }
    }

    @Transactional
    @CacheEvict(value = "studentsCache", allEntries = true)
    public void update(Long studentId, String name, String email) {
        Student std = this.stdrep.findById(studentId).orElseThrow(() ->
                new IllegalStateException("this has no student"));

        if(name != null && name.length() > 0 && !Objects.equals(name, std.getName())) {
            std.setName(name);
        }
        if(email != null && email.length() > 0 && !Objects.equals(email, std.getEmail())) {
            std.setEmail(email);
        }
    }
}


Step 6: Enable Hibernate 2nd-level caching

In application.properties, add:

# Hibernate 2nd level cache
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.region.factory_class=jcache
spring.jpa.properties.hibernate.javax.cache.provider=org.ehcache.jsr107.EhcacheCachingProvider
spring.jpa.properties.hibernate.cache.use_query_cache=true


Step 7: Annotate the entity for caching

In Student entity:

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

*****be careful to use theseeeeeee importssssssss

@Entity
@Cacheable

for the class for 2 -level cachingggggggggg

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
or but the first one is better less imports.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student {
    ...
}



Step 8: Enable Query Cache for repository queries

Example in studentRepository:


**********this to store what we bring what we bring from db we store in cach
**********i choose the methods that the most useddddddddddd
@Query("SELECT s FROM Student s WHERE s.name LIKE %:sub%")
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
**********and i can change strategy
******if we updated on cache the db will be updated automatically
****only if the stratigy  allows.
List<Student> findByNameContaining(@Param("sub") String sub, Sort sort);

******@QueryHints(@QueryHint(name = "org.hibernate.cacheable",
value = "true")) for methods with queres and
@Cacheable(value="studentsCache", key="#id")
 and this when we dont have query..
 so this for query checking
methods and this @Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
 for entity /class******
Why:

Query cache caches results of HQL/JPQL queries.

READ_ONLY is fine if you don’t modify the underlying data frequently.




lastly*******for query caching in custom interfaces you go to the implementation and add
@QueryHints(@QueryHint(name = "org.hibernate.cacheable",
value = "true"))


















 *****for mango db caching is easier :you need to do
 1. the first four steps r the same
 2.Step 5: Annotate methods for caching:

In your StudentService:

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

@Service
public class StudentService {

    // Cache this method result
    @Cacheable(value = "studentsCache")
    **************this because it is a list /and this is the name of region factory
    public List<Student> getStudent() {
        return stdrep.findAll();
    }

    // Evict cache on add/delete/update
    @CacheEvict(value = "studentsCache", allEntries = true)


    ************* this annotation to do cache validationnnnn
    so now with every crud op we have to use this so
    when updation db we update cache too*****

    **********amd the allentries=true means we clear it before every methodd****
    public void addStudent(Student std) {
        Optional<Student> studentByEmail = this.stdrep.findStudentByEmail(std.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("email is taken");
        }
        this.stdrep.save(std);
    }

    @CacheEvict(value = "studentsCache", allEntries = true)
    public void delete(Long studentId) {
        boolean exists = this.stdrep.existsById(studentId);
        if(exists){
            this.stdrep.deleteById(studentId);
        } else {
            throw new NullPointerException("student not found");
        }
    }

in update we useput

  *******  @Transactional
    @CachePut(value = "studentsCache", allEntries = true)*********
    public void update(Long studentId, String name, String email) {
        Student std = this.stdrep.findById(studentId).orElseThrow(() ->
                new IllegalStateException("this has no student"));

        if(name != null && name.length() > 0 && !Objects.equals(name, std.getName())) {
            std.setName(name);
        }
        if(email != null && email.length() > 0 && !Objects.equals(email, std.getEmail())) {
            std.setEmail(email);
        }
    }
}
3. step6:logging.level.org.springframework.cache=DEBUG  we add this in properties(optional)  to see cache operations
4. step 7:we dont add anythign in employeeeeee at allll
5. is the repository we add anotations to udpade the cache too
like:
    @Cacheable(value="studentsCache", key="#emailID")
and @Cacheable(value = "employeesCache", key = "#first + '_' + #last")
 for non query methods.
 */













