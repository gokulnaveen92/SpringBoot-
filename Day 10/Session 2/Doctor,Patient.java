1)//Patient

package com.examly.springapp;
 
public class Patient {
   
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
    @Override
    public String toString() {
        return "Patient [id=" + id + ", name=" + name + "]";
    }
  
}

2)//Doctor
  
package com.examly.springapp;
 
public class Doctor {
   
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
    @Override
    public String toString() {
        return "Doctor [id=" + id + ", name=" + name + "]";
    }
 
   
   
}
 
3)//AppoinmentService
  
package com.examly.springapp;
 
public class Doctor {
   
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
    @Override
    public String toString() {
        return "Doctor [id=" + id + ", name=" + name + "]";
    }
  
}

4)//XML
  <?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
 
    <bean name="doctor" class="com.examly.springapp.Doctor">
    </bean>
   
    <bean name="patient" class="com.examly.springapp.Patient">
    </bean>
 
    <bean name="appointmentService" class="com.examly.springapp.AppointmentService" autowire="byName">
    </bean>
 
</beans>

  5)//springfile
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
        Patient obj1 = ctx.getBean("patient", Patient.class);
        obj1.setId(01);
        obj1.setName("Gokul");
        Doctor obj2 = ctx.getBean("doctor",Doctor.class);
        obj2.setId(001);
        obj2.setName("Dr.Janhavi");
        AppointmentService obj = ctx.getBean("appointmentService",AppointmentService.class);
        obj.setPatient(obj1);
        obj.setDoctor(obj2);
        System.out.println(obj.toString());
       
}
 
}

  
 
