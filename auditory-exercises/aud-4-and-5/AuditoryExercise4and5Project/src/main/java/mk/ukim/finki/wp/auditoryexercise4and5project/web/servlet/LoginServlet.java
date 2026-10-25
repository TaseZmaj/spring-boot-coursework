package mk.ukim.finki.wp.auditoryexercise4and5project.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.User;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.AuthService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService;
    private final SpringTemplateEngine templateEngine;

    public LoginServlet(AuthService authService, SpringTemplateEngine templateEngine) {
        this.authService = authService;
        this.templateEngine = templateEngine;
    }

    //GET - Ja prikazuva login formata
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);
        templateEngine.process("login.html", context, resp.getWriter());
    }

    //POST - procesira login attempt
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user;
        try{
            user = this.authService.login(username, password);
        }catch(RuntimeException ex){
            //Na unsuccessful login, re-render the login page with error
            context.setVariable("error", ex.getMessage());
            templateEngine.process("login.html", context, resp.getWriter());
            return;
        }

        //Na successful login, store the user in session and redirect
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/servlet/thymeleaf/category");
    }

}
