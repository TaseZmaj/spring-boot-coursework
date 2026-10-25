package mk.ukim.finki.wp.lab4.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.lab4.model.Book;
import mk.ukim.finki.wp.lab4.service.AuthorService;
import mk.ukim.finki.wp.lab4.service.BookReservationService;
import mk.ukim.finki.wp.lab4.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookReservationService bookReservationService;

    @GetMapping
    public String getAllBooksPage(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) Double averageRating,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String error,
            HttpServletRequest req,
            Model model){

        if (error != null) model.addAttribute("error", error);

        List<Book> books = bookService.find(searchQuery, averageRating, authorId);

        model.addAttribute("booksList", books);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("searchQuery", "");
        model.addAttribute("rating", 0.0);
        model.addAttribute("reservations", bookReservationService.listAll());
        model.addAttribute("userIpAddress", req.getRemoteAddr());
        model.addAttribute("username", req.getRemoteUser());

        return "listBooks";
    }

    //Mora da si postoi kako metod bez (params={}) za da moze da raboti samo ova /books
//    @GetMapping
//    public String getAllBooks(
//            @RequestParam(required = false) String error,
//            HttpServletRequest req,
//            Model model) {
//
//
//        if (error != null) model.addAttribute("error", error);
//
//        model.addAttribute("booksList", bookService.listAll());
//        model.addAttribute("authors", authorService.findAll());
//        model.addAttribute("searchQuery", "");
//        model.addAttribute("rating", 0.0);
//        model.addAttribute("reservations", bookReservationService.listAll());
//        model.addAttribute("userIpAddress", req.getRemoteAddr());
//
//        return "listBooks";
//    }
//
//    //Se koristi za filtriranje preku searchQuery i rating
//    @GetMapping(params={"searchQuery", "rating"})
//    public String getBooksPage(
//            @RequestParam(required = false, defaultValue= "") String searchQuery,
//            @RequestParam(required = false, defaultValue= "0.0") String rating,
//            @RequestParam(required = false) String error,
//            HttpServletRequest req,
//            Model model){
//        if(error != null){
//            model.addAttribute("error", error);
//        }
//
//        List<Book> books = bookService.searchBooks(searchQuery, Double.parseDouble(rating));
//
//
//        model.addAttribute("booksList", books);
//        model.addAttribute("authors", authorService.findAll());
//        model.addAttribute("searchQuery", searchQuery);
//        model.addAttribute("rating", rating);
//        model.addAttribute("reservations", bookReservationService.listAll());
//        model.addAttribute("userIpAddress", req.getRemoteAddr());
//
//        return "listBooks";
//    }
//
//    //Se koristi za filtriranje preku Avtori
//    @GetMapping(params="authorId")
//    public String getBooksPage(
//            @RequestParam(required = false) String error,
//            @RequestParam(required = false, defaultValue= "") String authorId,
//            HttpServletRequest req,
//            Model model){
//
//        List<Book> books = bookService.searchBooks(Long.parseLong(authorId));
//
//        if(error != null){
//            model.addAttribute("error", error);
//        }
//        model.addAttribute("booksList", books);
//        model.addAttribute("authors", authorService.findAll());
//        model.addAttribute("searchQuery", "");
//        model.addAttribute("rating", 0.0);
//        model.addAttribute("reservations", bookReservationService.listAll());
//        model.addAttribute("userIpAddress", req.getRemoteAddr());
//
//        return "listBooks";
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/book-form/{id}")
    public String getEditBookForm(@PathVariable Long id, Model model){
        model.addAttribute("bookId", id);
        if(id == null || id == 0L){
            return "redirect:/books?error=BookNotFound"; //P.S. Nema da se prikaze error-ot
        }

        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("book", bookService.findById(id));

        return "book-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/book-form")
    public String getAddBookForm(Model model){
        model.addAttribute("authors", authorService.findAll());
        return "book-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String saveBook(@RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam Double averageRating,
                           @RequestParam Long authorId) {
        bookService.create(title, genre, averageRating, authorId);
        return "redirect:/books";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("edit/{bookId}")
    public String editBook(@PathVariable Long bookId,
                           @RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam Double averageRating,
                           @RequestParam Long authorId){
        bookService.update(bookId, title, genre, averageRating, authorId);
        return "redirect:/books";
    }
}
