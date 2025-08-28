package com.example.demo;

import com.example.demo.Student.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	//so jpa fpr connecting with the data base
	//autowired to make instance of an object
			//service this class is respnsible for business logic.
	//@repositry means that this class is responsible with fetching the data

	//command line runner means when sth  happens first before everything hppens
	//@transient means that this data feild no longer have a column in our data class.
	//because here in age we want it calculated not have it own column since we have dop
	//so first cjpa and the interface are for dealing with data base /
	//then we have controller for http requsts
	//then we have service for for logic
	//and to keep evrything nic and seperate we do object from rep in service and object from service in controller

	//@Query to enter an jpaql query.
	//@Pathvariable means to take the variable from the path
	//
}
// bean lifecycle we have three ways to do it one in the xml file
		//by using init-method  and destroy-method
//2 by using interface that is initalizingBean  ns DisposableBean and using
          //using @setPropritiesSet for init and @destroy
//and lastly by simply using those and then the methods and
// then context.close to trigger the preDestroy
         //@postContrust and @preDestroy