package org.example.firstspringbootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//Ova morame da go dodademe bidejki po default Spring Boot koga ke go povika main() metodot - toj skenira samo @Controllers
//bidejki se podobri i ponovi od @WebServlet.  Za da skenira i servleti morame racno da ja dopisheme ovaa anotacija
@ServletComponentScan
@SpringBootApplication
public class FirstSpringbootAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstSpringbootAppApplication.class, args);
    }

}
