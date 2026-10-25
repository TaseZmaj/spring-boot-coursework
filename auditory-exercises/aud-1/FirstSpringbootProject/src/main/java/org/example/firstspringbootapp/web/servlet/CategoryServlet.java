package org.example.auditoryexercise2.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="category-servlet", urlPatterns="/servlet/category")
public class CategoryServlet extends HttpServlet{

    class Category{
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Category(String name){
            this.name=name;
        }
    }

    private List<Category> categoryList = null;

    //nema potreba ovoj init() racno da go pishuvash, spring samo avtomatski koa ke se startuva aplikacijata go sreduva,
    //tuka samo go pishav za da vidis ustvari shto se slucuva
    @Override
    public void init() throws ServletException {
        super.init();
        //od tuka nadole e moj kod custom - generiranje dummy data
        categoryList = new ArrayList<Category>();
        addCategory("Software");
        addCategory("Books");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp); // Iskomentiraj go ova za da raboti inaku ke ti go dava default metodot od Servletot - shto vrakja whitepage
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet CategoryServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet CategoryServlet</h1>");
        out.println("<ul>");
        for(Category category:categoryList){
            out.println("<li>"+category.getName()+"</li>");
        }
        out.println("<ul/>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
    }

    public void addCategory(String name){
        if(name != null && !name.isEmpty()){
            categoryList.add(new Category(name));
        }
    }
}

