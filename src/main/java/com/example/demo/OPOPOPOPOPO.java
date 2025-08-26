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





 */