package mk.ukim.finki.wp.auditoryexercise4and5project.web.filter;//package org.example.auditoryexercise2.web.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.example.auditoryexercise2.model.User;
//
//import java.io.IOException;
//
//@WebFilter
//public class LoginFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse resp = (HttpServletResponse) servletResponse;
//
//        User loggedInUser = (User) req.getSession().getAttribute("user");
//        String path = req.getServletPath();
//
//        //Jas gi dodadov ovie
//        boolean isLoginPage = path.equals("/login");
//        boolean isStatic = path.endsWith(".css") || path.endsWith(".js")
//                || path.endsWith(".png") || path.endsWith(".jpg")
//                || path.startsWith("/images/") || path.startsWith("/css/");
//
////        !path.equals("/login")
//        if(loggedInUser == null && !isLoginPage && !isStatic) {
//            resp.sendRedirect("/login");
//        }else{
//            //Kod pred linijava se executenuva koga filterot ke oseti Request od klientot
//            System.out.println("Filter triggered na idenje"); //moj kod
//
//            filterChain.doFilter(req, resp); // mu kazuva na filterot da si prodolzi
//
//            //Kod posle linijava se executenuva koga nekoj servlet ke generira response
//            System.out.println("Filter triggered na vrakjanje"); //moj kod
//        }
//    }
//}
