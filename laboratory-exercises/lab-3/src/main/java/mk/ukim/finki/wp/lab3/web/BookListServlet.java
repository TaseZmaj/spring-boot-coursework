package mk.ukim.finki.wp.lab3.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab3.model.Book;
import mk.ukim.finki.wp.lab3.service.BookReservationService;
import mk.ukim.finki.wp.lab3.service.BookService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

//@WebServlet(name="BooklistServlet")
public class BookListServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final BookService bookService;
    private final BookReservationService bookReservationService;

    public BookListServlet(SpringTemplateEngine springTemplateEngine, BookService bookService, BookReservationService bookReservationService) {
        this.springTemplateEngine = springTemplateEngine;
        this.bookService = bookService;
        this.bookReservationService = bookReservationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

//        Mora da se vaka pishani bidejki vo sprotivno ke bidat null ako nema nishto vo
        //query-to gore, pa ke krene error serverot i vrakja status 500
        String searchQuery = req.getParameter("searchQuery") == null ? "" : req.getParameter("searchQuery");
        String rating  = req.getParameter("rating") == null || req.getParameter("rating").isEmpty() ? "0.0" : req.getParameter("rating");
        context.setVariable("reservations", bookReservationService.listAll());

        List<Book> books = bookService.searchBooks(searchQuery, Double.parseDouble(rating));

        context.setVariable("booksList", books);
        context.setVariable("userIpAddress", req.getRemoteAddr());

        springTemplateEngine.process("listBooks.html", context, resp.getWriter());
    }
}
