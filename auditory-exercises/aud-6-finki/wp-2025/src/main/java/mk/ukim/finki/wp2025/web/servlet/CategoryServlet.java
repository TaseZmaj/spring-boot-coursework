package mk.ukim.finki.wp2025.web.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp2025.service.CategoryService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "CategoryServlet", urlPatterns = "/servlet/category")
public class CategoryServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final CategoryService categoryService;

    public CategoryServlet(SpringTemplateEngine springTemplateEngine, CategoryService categoryService) {
        this.springTemplateEngine = springTemplateEngine;
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("userAgent", req.getHeader("user-agent"));
        context.setVariable("errorMessage", req.getParameter("errorMessage"));
        context.setVariable("categories", this.categoryService.listCategories());

        // Update and retrieve the user views count from the servlet context
        Integer userViews = (Integer) getServletContext().getAttribute("userViews");
        getServletContext().setAttribute("userViews", userViews + 1);
        context.setVariable("userViews", userViews);

        springTemplateEngine.process("categories.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        try {
            // Service layer handles validation and throws exceptions on invalid input
            this.categoryService.create(name, description);
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("/servlet/category?errorMessage=Invalid input for category");
            return;
        }

        resp.sendRedirect("/servlet/category");
    }
}

