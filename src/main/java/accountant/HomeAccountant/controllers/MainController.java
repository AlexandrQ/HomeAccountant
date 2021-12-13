package accountant.HomeAccountant.controllers;

import accountant.HomeAccountant.models.Record;
import accountant.HomeAccountant.repo.RecordRepository;
import accountant.HomeAccountant.utils.ChartsUtils;
import accountant.HomeAccountant.utils.RecordsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private RecordRepository recordRepository;

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("title", "Home page");
        model.addAttribute("name", "Guest");
        return "home";
    }

    @GetMapping("/google-charts")
    public String googleChart(Model model) {
        List<Record> allRecords = (List<Record>) recordRepository.findAll();
        model.addAttribute("columnChartData", ChartsUtils.getColumnChartsMap(allRecords));
        model.addAttribute("barchartData", ChartsUtils.getBarChartsMap(allRecords));
        model.addAttribute("areaChartData", ChartsUtils.getAreaChartsMap(allRecords));
        return "google-charts";
    }

    @GetMapping("/records")
    public String records(Model model) {
        Iterable<Record> records = recordRepository.findAll();
        model.addAttribute("records", records);
        return "records";
    }

    @PostMapping("/records")
    public String recordsPost(@RequestParam("file") MultipartFile file, Model model) {
        List<Record> records = RecordsUtils.parseCsv(file);
        if(!records.isEmpty()) {
            recordRepository.saveAll(records);
        }
        model.addAttribute("records", records);
        return "records";
    }
}
