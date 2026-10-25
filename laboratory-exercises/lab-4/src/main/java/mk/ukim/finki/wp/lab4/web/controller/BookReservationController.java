package mk.ukim.finki.wp.lab4.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.lab4.service.BookReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/bookReservation")
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class BookReservationController {
    private final BookReservationService bookReservationService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public String createReservation(
            @RequestParam String bookTitle,
            @RequestParam String readerName,
            @RequestParam String readerAddress,
            @RequestParam long numberOfCopies,
            HttpServletRequest req,
            Model model){

        model.addAttribute("userIpAddress",req.getRemoteAddr());
        model.addAttribute("bookTitle",bookTitle);
        model.addAttribute("readerName",readerName);
        model.addAttribute("readerAddress",readerAddress);
        model.addAttribute("numberOfCopies",numberOfCopies);

        bookReservationService.placeReservation(bookTitle, readerName, readerAddress, numberOfCopies);
        return "reservationConfirmation";
    }
}
