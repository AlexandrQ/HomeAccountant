package accountant.HomeAccountant.controllers;

import accountant.HomeAccountant.models.Record;
import accountant.HomeAccountant.repo.RecordRepository;
import accountant.HomeAccountant.utils.RecordsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
        Map<String, List<Record>> recordsMap = ((List<Record>) recordRepository.findAll()).stream()
                .collect(Collectors.groupingBy(rec -> rec.getDate().format(DateTimeFormatter.ofPattern("yyyy-MMM"))));
        Map<String, Integer> graphData = new TreeMap<>();
        for(Map.Entry<String, List<Record>> entry : recordsMap.entrySet()) {
            graphData.put(entry.getKey(), entry.getValue().stream().map(Record::getSum).reduce(Integer::sum).orElse(0));
        }
        model.addAttribute("chartData", graphData);
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
