package mk.ukim.finki.wp.lab4.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.lab4.service.BookReservationService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

//@WebServlet(name="BookReservationServlet", urlPatterns = "/bookReservation")
public class BookReservationServlet extends HttpServlet {
    private final BookReservationService bookReservationService;
    private final SpringTemplateEngine springTemplateEngine;

    public BookReservationServlet(BookReservationService bookReservationService, SpringTemplateEngine springTemplateEngine) {
        this.bookReservationService = bookReservationService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange); //ovoj context e context-ot od thymeleaf, ne e isto so ServletContext

        context.setVariable("bookTitle", req.getSession().getAttribute("bookTitle"));
        context.setVariable("readerName", req.getSession().getAttribute("readerName"));
        context.setVariable("readerAddress", req.getSession().getAttribute("readerAddress"));
        context.setVariable("numCopies", req.getSession().getAttribute("numCopies"));
        context.setVariable("userIpAddress", req.getSession().getAttribute("userIpAddress"));

        springTemplateEngine.process("reservationConfirmation.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookTitle = req.getParameter("bookTitle");
        String readerName =  req.getParameter("readerName");
        String readerAddress = req.getParameter("readerAddress");
        String numCopies = req.getParameter("numCopies");
        String userIpAddress = req.getParameter("userIpAddress");

        try{
            HttpSession session = req.getSession();
            session.setAttribute("bookTitle", bookTitle);
            session.setAttribute("readerName", readerName);
            session.setAttribute("readerAddress", readerAddress);
            session.setAttribute("numCopies", numCopies);
            session.setAttribute("userIpAddress", userIpAddress);

            bookReservationService.placeReservation(bookTitle, readerName, readerAddress, Long.parseLong(numCopies));
        }catch(Exception e){
            resp.sendRedirect("/?errorMessage=invalid_input" + e.getLocalizedMessage());
        }
        resp.sendRedirect("/bookReservation"); //ova SEKOGAS vrakja 302 status na klientot i odma posle toa pravi HTTP GET Request do "/bookReservation"
    }
}
