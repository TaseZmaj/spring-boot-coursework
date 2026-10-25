package org.example.auditoryexercise2.web.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.auditoryexercise2.service.CategoryService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "thymeleaf-category-servlet", urlPatterns = "/servlet/thymeleaf/category")
public class ThymeleafCategoryServlet extends HttpServlet {

    //SHABLON za Thymeleaf
    private final SpringTemplateEngine springTemplateEngine;
    private final CategoryService categoryService;

    ThymeleafCategoryServlet(SpringTemplateEngine springTemplateEngine, CategoryService categoryService) {
        this.springTemplateEngine = springTemplateEngine;
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //OVA NE RABOTI, STARA VERZIJA NA THYMELEAF E
//        WebContext context = new WebContext(req, resp, req.getServletContext());

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("clientAgent", req.getHeader("User-Agent"));
        context.setVariable("categories", this.categoryService.listCategories());
        context.setVariable("errorMessage", req.getParameter("errorMessage"));

        //Global counter logika - inkrementiranje
        //Sekoj pat koga ke se povika ovoj doGet() - se updatenuva GLOBALNIOT context, t.e. varijablata
        //userViews koja ja definirase vo @WebListener-ot
        Integer userViews = (Integer) getServletContext().getAttribute("userViews");
        getServletContext().setAttribute("userViews", userViews + 1);
        context.setVariable("userViews", userViews);

        springTemplateEngine.process("categories.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryName = req.getParameter("name");
        String categoryDescription = req.getParameter("description");

        try{
            categoryService.create(categoryName, categoryDescription);
        }catch(Exception e){
            resp.sendRedirect("/servlet/thymeleaf-category-servlet?errorMessage=invalid_input_for_category");
        }
        resp.sendRedirect("/servlet/thymeleaf/category");
    }
}
