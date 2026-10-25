package mk.ukim.finki.wp2025.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloWorldServlet", urlPatterns = {"/hello"})
public class HelloWorldServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // initialize the writer
        PrintWriter writer = resp.getWriter();

        // get the name and surname parameters from the request
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");

        if (name == null) {
            name = "X";
        }

        if (surname == null) {
            surname = "Y";
        }

        // write the response
        writer.format("<html><head></head><body><h1>Hello %s %s</h1></body></html>", name, surname);

        // flush the writer
        writer.flush();
    }
}
