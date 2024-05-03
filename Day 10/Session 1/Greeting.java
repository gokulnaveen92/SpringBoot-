1)//greeting
package com.examly.springapp;

public class GreetingMessage {
    String msg;

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }
}

2)//xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    
<bean id="greetingMessage" class="com.examly.springapp.GreetingMessage">
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
		GreetingMessage obj = ctx.getBean("greetingMessage",GreetingMessage.class);
		obj.setMsg("Good Morning");
		System.out.println(obj.getMsg());
          
	}

}
