package org.example.auditoryexercise2.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.auditoryexercise2.model.Category;
import org.example.auditoryexercise2.service.CategoryService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="category-servlet", urlPatterns="/servlet/category")
    public class CategoryServlet extends HttpServlet{

    private final CategoryService categoryService;

    public CategoryServlet(CategoryService categoryService) {
        //Tuka gi startuvash site servisi shto gi koristis vo ovoj servlet
        this.categoryService = categoryService;
    }

    //REFAKTORIRANO vo /model/Category.java
//    class Category{
//        private String name;
//        private String description;
//
//        public void setName(String name, String description) {
//            this.name = name;
//            this.description = description;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public Category(String name){
//            this.name=name;
//            this.description="";
//        }
//        public Category(String name, String description){
//            this.name=name;
//            this.description=description;
//        }
//
//    }
    //REFAKTORIRANO vo DataHolder.java
// private List<Category> categoryList = null;

    //REFAKTORIRANO VO DataHolder.java
//nema potreba ovoj init() racno da go pishuvash, spring samo avtomatski koa ke se startuva aplikacijata go sreduva,
//tuka samo go pishav za da vidis ustvari shto se slucuva
//@Override
//    public void init() throws ServletException {
//        super.init();
//        //od tuka nadole e moj kod custom - generiranje dummy data
//        categoryList = new ArrayList<Category>();
//        this.categoryList.add(new Category("Software", "A bundle of OS software, 3D software and more!"));
//        this.categoryList.add(new Category("Books", "Story books, Science books, Philosophy Books and more!"));
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Iskomentiraj go ova za da raboti inaku ke ti go dava default metodot od Servletot - shto vrakja whitepage
        // super.doGet(req, resp);

        PrintWriter out = resp.getWriter();
        String ClientIPaddress = req.getRemoteAddr();
        String clientAgent = req.getHeader("User-Agent");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet CategoryServlet</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h1> Client info </h1>");
        out.println("<p>Client IP address: " +  ClientIPaddress + "</p>");
        out.println("<p>Client User agent: " +  clientAgent + "</p>");

        out.println("<h1>Category list</h1>");
            out.println("<ul>");
            //REFAKTORIRANO so pomosh na /service/implementations/CategoryServiceImpl.java
//            for(Category category:categoryList){
//                out.println("<li>"+category.getName()+" | Description: " + category.getDescription() + "</li>");
//            }
        //
        for(Category category: categoryService.listCategories()){
            out.println("<li>"+category.getName()+" | Description: " + category.getDescription() + "</li>");
        }
            out.println("</ul>");

        out.println("<h3>Add category</h3>");
        out.println("<form method='POST' action='/servlet/category'>");
            out.println("<label for='name'>Name</label>");
            out.println("<input type='text' name='name' required>");
            out.println("<label for='description'>Description</label>");
            out.println("<input type='text' name='description' required>");
            out.println("<input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryName = req.getParameter("name"); //ova name e imeto e ustvari od <input name="name" />
        String categoryDescription = req.getParameter("description");
//        addCategory(categoryName, categoryDescription); //ova e ne potrebno vekje BIDEJKI...
        //Se sluzime so category servisot
        try{
            categoryService.create(categoryName, categoryDescription);
        }catch(Exception e){
            resp.sendRedirect("/servlet/thymeleaf-category-servlet?errorMessage=invalid_input_for_category");
        }
        resp.sendRedirect("/servlet/category");
    }

    //VEKJE NE E RELEVANTNO PO REFAKTORIRANJETO
//    public void addCategory(String name, String description){
//        if(name != null && !name.isEmpty() && description != null && !description.isEmpty()){
//            categoryList.add(new Category(name, description));
//        }
//    }
}

