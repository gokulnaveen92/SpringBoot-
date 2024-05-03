1)//helloworld
package com.examly.springapp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/api")
    public String helloWorld(){
        return "Hello World";
    }
}
