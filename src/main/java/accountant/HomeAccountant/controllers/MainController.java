package accountant.HomeAccountant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.TreeMap;

@Controller
public class MainController {
    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("title", "Home page");
        model.addAttribute("name", "Guest");
        return "home";
    }

    @GetMapping("/google-charts")
    public String googleChart(Model model) {
        Map<String, Integer> graphData = new TreeMap<>();
        graphData.put("2016", 147);
        graphData.put("2017", 1256);
        graphData.put("2018", 3856);
        graphData.put("2019", 19807);
        model.addAttribute("chartData", graphData);
        return "google-charts";
    }
}
