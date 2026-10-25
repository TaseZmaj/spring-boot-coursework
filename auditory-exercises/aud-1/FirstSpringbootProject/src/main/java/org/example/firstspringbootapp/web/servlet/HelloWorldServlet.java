package org.example.firstspringbootapp.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

//Ova seuste NE e Springboot kod.  Ova e Servlet, koj e del od Java Jakarta Servlet API
//Ova e RAW nacinot da se handlene HTTP Request pred da postojat Spring ili bilo koj drug framework

//name="hello" e logickoto ime na Servlet-ot,
//urlPatterns znaci deka ovoj Servlet ke handle-ne bilo koj HTTP request koj ke dojde na url-to /hello kako na pr. http://localhost:8080/hello
//...extends HttpServlet znaci deka nasleduva klasata koja gi sodrzi metodite za handlanje HTTP Request-ovi kako doGet() i doPost()

//Ovoj metod se povikuva avtomatski koga HTTP GET Request ke stigne na "/hello"
//HttpServletRequest req — represents the incoming request (headers, URL params, etc.)
//HttpServletResponse resp — represents the outgoing response (status, headers, body you send back).

@WebServlet(name="hello", urlPatterns="/hello")
public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String age = req.getParameter("age");

        if(name == null || age == null ){
            name = "Guest!";
            age = "0";
        }

        System.out.println("Hello " + name + " " + age);

        PrintWriter writer = resp.getWriter();
        writer.write("<html>" +
                "<head></head>" +
                "<body>" +
                "<h1>Hello " + name + "!</h1>" +
                "<h2> You are " + age + " years old!</h2>" +
                "</body>" +
                "</html>");
        writer.flush(); //go prakja napishaniot responce od writerod DIREKTNO na klientot
    }
}
