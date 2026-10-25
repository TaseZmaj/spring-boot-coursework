package mk.ukim.finki.wp2025.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping
    public String logout(HttpServletRequest req) {
        req.getSession().invalidate();
        return "redirect:/login";
    }
}
