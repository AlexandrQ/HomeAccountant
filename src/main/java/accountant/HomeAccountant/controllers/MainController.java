package accountant.HomeAccountant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("title", "Home page");
        model.addAttribute("name", "Guest");
        return "home";
    }
}
