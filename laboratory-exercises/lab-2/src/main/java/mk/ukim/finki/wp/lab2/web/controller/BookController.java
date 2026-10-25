package mk.ukim.finki.wp.lab2.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.lab2.model.Author;
import mk.ukim.finki.wp.lab2.model.Book;
import mk.ukim.finki.wp.lab2.service.AuthorService;
import mk.ukim.finki.wp.lab2.service.BookReservationService;
import mk.ukim.finki.wp.lab2.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookReservationService bookReservationService;

    @GetMapping()
    public String getBooksPage(
            HttpServletRequest req,
            @RequestParam(required = false, defaultValue= "") String searchQuery,
            @RequestParam(required = false, defaultValue= "0.0") String rating,
            @RequestParam(required = false) String error,
            Model model){
        if(error != null){
            model.addAttribute("error", error);
        }

        List<Book> books = bookService.searchBooks(searchQuery, Double.parseDouble(rating));

        model.addAttribute("booksList", books);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("rating", rating);
        model.addAttribute("reservations", bookReservationService.listAll());
        model.addAttribute("userIpAddress", req.getRemoteAddr());

        return "listBooks";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/add")
    public String saveBook(@RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam Double averageRating,
                           @RequestParam Long authorId) {
        bookService.create(title, genre, averageRating, authorId);
        return "redirect:/books";
    }

    @PostMapping("edit/{bookId}")
    public String editBook(@PathVariable Long bookId,
                           @RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam Double averageRating,
                           @RequestParam Long authorId){
        bookService.update(bookId, title, genre, averageRating, authorId);
        return "redirect:/books";
    }

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

    @GetMapping("/book-form")
    public String getAddBookPage(Model model){
        model.addAttribute("authors", authorService.findAll());
        return "book-form";
    }
}
