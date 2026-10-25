package org.example.auditoryexercise2.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.auditoryexercise2.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(
        filterName = "auth-filter",
        urlPatterns = "/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        initParams = { //tuka se definiraat Parametri na servlet
                @WebInitParam(name="ignore-path", value="/login"),
                @WebInitParam(name="ignore-stylesheet", value="/css/main.css")
        }
)
public class AuthFilter implements Filter {
    private List<String> ignorePaths; //ova e moe, ne beshe Lista vo auditoriska, jas ja napraviv

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        //definiraniot parametar od gore go zemas i koristis tuka
        ignorePaths = new ArrayList<>();
        this.ignorePaths.add(filterConfig.getInitParameter("ignore-path"));
        this.ignorePaths.add(filterConfig.getInitParameter("ignore-stylesheet"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        User loggedInUser = (User) req.getSession().getAttribute("user");
        String path = req.getServletPath();

        if(loggedInUser == null && !this.ignorePaths.contains(path)) {
            resp.sendRedirect("/login");
        }else{
            filterChain.doFilter(req,resp);
        }

    }
}
