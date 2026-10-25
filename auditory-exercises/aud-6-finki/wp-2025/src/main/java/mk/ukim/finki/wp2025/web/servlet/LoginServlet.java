package mk.ukim.finki.wp2025.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp2025.model.User;
import mk.ukim.finki.wp2025.service.AuthService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = "/servlet/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService;
    private final SpringTemplateEngine templateEngine;

    public LoginServlet(AuthService authService, SpringTemplateEngine templateEngine) {
        this.authService = authService;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        templateEngine.process("login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user;

        try {
            user = this.authService.login(username, password);
        } catch (RuntimeException e) {
            // On login failure, re-render the login page with an error message
            context.setVariable("error", e.getMessage());
            templateEngine.process("login.html", context, resp.getWriter());

            return;
        }

        // On successful login, store the user in the session and redirect to the categories page
        req.getSession().setAttribute("user", user);

        resp.sendRedirect("/products");
    }
}
