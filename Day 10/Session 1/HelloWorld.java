1)//hello java
package com.examly.springapp;

public class HelloWorld {
    private String msg;

    public String getMsg(){
        return msg;
    }
    
    public void setMsg(String ms){
        this.msg = ms;
    }
}

2)//xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    

<bean id="helloWorld" class="com.examly.springapp.HelloWorld">
</bean>

</beans>
  
3)//springfile
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
		HelloWorld obj = ctx.getBean("helloWorld",HelloWorld.class);
        obj.setMsg("Hello World!");
		System.out.println(obj.getMsg());  
	}

}
