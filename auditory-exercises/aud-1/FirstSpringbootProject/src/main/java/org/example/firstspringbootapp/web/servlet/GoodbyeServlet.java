package org.example.firstspringbootapp.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="goodbye", urlPatterns = "/goodbye")
public class GoodbyeServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.write("<html>" +
                "<head></head>" +
                "<body>" +
                "<h1>Goodbye!! :D</h1>" +
                "</body>" +
                "</html>");
        out.flush();
    }
}
