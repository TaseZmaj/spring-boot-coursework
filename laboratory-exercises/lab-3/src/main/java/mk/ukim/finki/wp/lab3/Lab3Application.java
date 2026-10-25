package mk.ukim.finki.wp.lab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Lab3Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab3Application.class, args);
    }
}
