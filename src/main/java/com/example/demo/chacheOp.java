

//package com.example.demo;

//public class chacheOp {

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
    **************this becauseit is a list /and this is the name of region factory
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


-----------------------------------------------------------------------------------------------------------------------
now to build aop or aspect oriented programming:
1.Add dependency → You need Spring AOP (spring-boot-starter-aop).:
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
</dependencies>


Create an annotation → Something like @LogExecutionTime.

@Target(ElementType.METHOD)  ------>OYR TAGrgt is methods
@Retention(RetentionPolicy.RUNTIME) ---> do it when run time so spring will be able to see @ ahile programm is
running to apply the aspect
public @interface LogExcutionTime {--->custom annotation
}

Create an aspect class → This is where you tell Spring: “whenever you see @LogExecutionTime, run this code around the method.”

Write your logic → Measure start time, call the method, measure end time, print result.

Use it → Just put @LogExecutionTime above any method you want to measure.
------------------------------------------------------------------------------------------------------------------------------------------------------
testing with spring boot:
1.we add dependences :
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <version>3.3.2</version>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>     we include data base for integration testing but for unit testing we dont need it



2.Integration tests: load the whole Spring Boot context (controllers, services, repositories, database)
 and check everything works together.



 3.how to do it :
 1.@ExtendWith(SpringExtension.class)
 this tells junit5 to use spring testing framework .

 2.@SpringBootTest( ---> this starts all your spring application
 (service /controller/..... for testing)
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,-->creates a fake web environment
  instead of a real web server.
  classes = Application.class)--->tells spring which main application class to use as the starting point

  3.@AutoConfigureMockMvc:
  This automatically sets up MockMvc, which lets you simulate HTTP requests to your controllers without starting a real web server.

  4.@TestPropertySource(locations = "classpath:application-integrationtest.properties")
  this tells sring to use special properties file just for testing which override
  the normal properities. so it will not use the real one



7.The application-integrationtest.properties file:
in here u make other propertirs file and put other datbase to deal with tets. the same stuff but diffrent name .


8.
a.i do controllerFor integration test :
we put in it
 A.MockMvc-->@Autowired
private MockMvc mvc;
MockMvc is your tool for simulating HTTP requests (GET, POST, etc.) to test your REST endpoints.

B.the repository -->@Autowired
private EmployeeRepository repository;
This gives you direct access to your repository so you can set up test data or verify database operations.


C. ObjectMapper ------> so i dont have to write the json file myself i will make the object and it will convert

D.and set up to empty the repository(database @BeforeEach).:
@BeforeEach
    void setUp() {
        employeeRepository.deleteAll(); // Clean database before each test
    }


F.and that you create DATA and save it
private Employee createTestEmployee(String name, String email, String department) {
        Employee employee = new Employee(name, email, department);
        return employeeRepository.save(employee);
    }


 E.  and lastly you do @Test
 ******import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
 this is for get.
 after adding the dats @Test
public void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {
    createTestEmployee("bob"); ---> test data in the data base.

    mvc.perform(get("/api/employees")--->simulates a get request
      .contentType(MediaType.APPLICATION_JSON))-->content type of the request.
      .andExpect(status().isOk())---->verifies th eresponse statuse
      .andExpected(content(
      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)))--->verifies response is json
      .andExpected(jsonPath("$[0].name", is("bob")));----->verifies the first employee name is boob
}

so i am sending the request and what iam excpected to see as a result

so when i am sending the request get i expect in return so i use [0]/[1] but when i sent post with json body it depends
ifi return it so i just have to be careful what it returns
------------------------------------------------------------------------------------------------------------------------------
5. Test Configuration With @TestConfiguration: ****this alone doesnt work*****
****and it is a way of testing the service class without data base or anything
it is just service.


1.so  i create class with fake data and put it in a list  overrides the service class

2.and use @testConfiguration (on the class) and then @Bean @Primary and then make a method that returns
instance of the real service and keep it open like a mini class
 and put the fake data and the methods in it

3.and then i start @Override the method

4.and then i make other class and start it with @ExtendWith(SpringExtension.class) and @Import(theclass i put the fake data in.class)

5.and then @Autowired another instance of the real service class to be th e fake one so and i wont need a
constructor because it will understand that this is the same one
i get back from the method i did in the method in the first class
because it has @Bean above it .
-------------------------------------------------------------------------------------------------------------------------------------------------
Approach 1: @TestConfiguration + @MockITOBean
so here i use 1. @ExtendWith(SpringExtension.class) @SpringBootTest above the class

 2.then do mini class config in it and above
 this mini class @TestConfiguration

 3.and then i put @Bean @Primary and under it i put a method to return a new instance
 of the real service with mock repo and then i use @MockBean to actually mock the repo
 and @Autowired above the instance of service ad because i have a bean it will search
 for it and take it and because of the annotation of mock it will replace inside the service
 with this one

4.and then i am free to do tests using @test

----------------------------------------------------------------------------------------------------------------------
 Approach 2: @MockBean alone
 1.but in here i start with @ExtendWith(SpringExtension.class) @SpringBootTest above the class

 2.and then i mock the repo using @MockBean and again

 3.use @Autowired above the instance of the real service
 and because of the mock annotation it will understand to
 replace the one inside the service with the mock

 4.and then i am free to o the tests


 /**********thf first appraoch uses fake repo and i
  gotta redo theservice class and the second approch
  it uses real service but with mocked /fake repo and
  thats it
-----------------------------------------------------------------------------------------------------------------------------------------
7. Integration Testing With @DataJpaTest

Test database: Temporary H2 that gets created/
destroyed for each test

so this testing is to test database queres
so methods in repository /

 1.so first we do class and anotate it with @ExtendWith(SpringExtension.class)and @DataJpaTest

 2.and in it  i put 2 objects 1 TestEntityManger and the second object of real repository

 3./and then i write test and i use methods inside the manger(the entitytestmanager).
 ----------------------------------------------------------------------------------------------------------------------------------
unit testing:
in this test we start with @ExtendWith(SpringExtension.class) and @WebMvcTest(YourController.class)

2.and then @Autowired private MockMvc mockMvc;  @MockBean // Mock the  private YourService yourService;

3. and then i implement tests
     */




