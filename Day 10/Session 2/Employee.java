1)//employee
package com.examly.springapp;

public class Employee {
    private int id;
    private String name;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}

2)//company
package com.examly.springapp;

public class Company {
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public String display(){
        return employee.getId() + " " + employee.getName();
    }
}

3)//xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

<bean name="employee" class="com.examly.springapp.Employee">
</bean>
<bean name="company" class="com.examly.springapp.Company" autowire="byName">
</bean>

</beans>

  4)//springfile
package com.examly.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@SpringBootApplication
public class SpringappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringappApplication.class, args);
         ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		 Employee e = ctx.getBean("employee",Employee.class);
		 Company c = ctx.getBean("company",Company.class);
		 e.setId(24);
		 ae.setName("Gokul");
		 System.out.println(c.display());	

}

}
