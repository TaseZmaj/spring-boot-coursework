package mk.ukim.finki.wp.auditoryexercise4and5project.web.observer;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);

        //userViews E GLOBALNA ZA CELATA APLIKACIJA, NE e na nivo na servlet
        sce.getServletContext().setAttribute("userViews", 0);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        sce.getServletContext().removeAttribute("userViews");
    }
}
