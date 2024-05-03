1)//person
package com.examly.springapp;

public class Person {
    private String name;
    private int age;
    Address address;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public String display(){
        return "Name: "+name+" Age:"+age+" Address"+address.getAddress();
    }
}

2)//address
package com.examly.springapp;

public class Address {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

3)//xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
 
   <bean name="address" class="com.examly.springapp.Address">
   </bean>
   <bean name="person" class="com.examly.springapp.Person" autowire="byName">

   </bean>
    

</beans>

  4)//springfire
package com.examly.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringappApplication.class, args);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        Person obj = ctx.getBean("person",Person.class);
        Address adr = ctx.getBean("address", Address.class);
        adr.setAddress("Mumbai");
        obj.setAddress(adr);
        obj.setAge(20);
        obj.setName("Aakash");
        System.out.println(obj.display());
    }

}
