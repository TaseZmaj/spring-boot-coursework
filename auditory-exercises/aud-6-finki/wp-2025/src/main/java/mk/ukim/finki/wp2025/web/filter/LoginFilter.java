package mk.ukim.finki.wp2025.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp2025.model.User;

import java.io.IOException;

// Filter configured to intercept all requests except those to the login page
@WebFilter(
        filterName = "auth-filter", urlPatterns = "/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        initParams = {
                @WebInitParam(name = "login-path", value = "/login"),
                @WebInitParam(name = "home-path", value = "/home"),
                @WebInitParam(name = "register-path", value = "/register"),
                @WebInitParam(name = "db-path", value = "/h2")
        }
)
public class LoginFilter implements Filter {
    private String loginPath;
    private String homePath;
    private String registerPath;
    private String dbPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.loginPath = filterConfig.getInitParameter("login-path");
        this.homePath = filterConfig.getInitParameter("home-path");
        this.registerPath = filterConfig.getInitParameter("register-path");
        this.dbPath = filterConfig.getInitParameter("db-path");
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        User loggedInUser = (User) req.getSession().getAttribute("user");
        String path = req.getServletPath();

        if (loggedInUser == null && !this.loginPath.startsWith(path) &&
                !this.homePath.startsWith(path) &&
                !this.registerPath.startsWith(path) &&
                !this.dbPath.startsWith(path)) {
            resp.sendRedirect("/login");
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
