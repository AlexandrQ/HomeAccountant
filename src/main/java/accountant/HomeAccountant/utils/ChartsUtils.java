package accountant.HomeAccountant.utils;

import accountant.HomeAccountant.models.Record;
import org.springframework.data.util.Pair;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ChartsUtils {

    public static final String CHARTS_DATE_FORMAT = "yyyy-MM";
    public static final int OUTCOMES_MIN_LIMIT = 10000;

    public static Map<String, Integer> getColumnChartsMap(List<Record> records) {
        Map<String, List<Record>> recordsMap = records.stream()
                .collect(Collectors.groupingBy(rec -> rec.getDate().format(DateTimeFormatter.ofPattern(CHARTS_DATE_FORMAT))));
        return recordsMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                return entry.getValue().stream()
                        .map(Record::getSum)
                        .reduce(Integer::sum)
                        .orElse(0);}, Integer::sum, TreeMap::new));
    }

    public static Map<String, Integer> getBarChartsMap(List<Record> records) {
        Map<String, List<Record>> recordsMap = records.stream()
                .filter(rec -> rec.getSum() < 0)
                .collect(Collectors.groupingBy(Record::getComment));

        Map<String, Integer> result = new TreeMap<>();
        recordsMap.forEach((key, value) -> {
            int sum = value .stream()
                    .map(Record::getSum)
                    .reduce(Integer::sum)
                    .orElse(0) * -1;
            if (sum >= OUTCOMES_MIN_LIMIT) {
                result.put(key, sum);
            }
        });
        return result;
    }

    //TODO refactoring
    public static Map<String, Pair<Integer, Integer>> getAreaChartsMap(List<Record> records) {
        Map<String, List<Record>> incomeRecordsMap = records.stream()
                .filter(rec -> rec.getSum() > 0)
                .collect(Collectors.groupingBy(rec -> rec.getDate().format(DateTimeFormatter.ofPattern(CHARTS_DATE_FORMAT))));
        Map<String, List<Record>> outcomeRecordsMap = records.stream()
                .filter(rec -> rec.getSum() < 0)
                .collect(Collectors.groupingBy(rec -> rec.getDate().format(DateTimeFormatter.ofPattern(CHARTS_DATE_FORMAT))));

        Set<String> dateKeys = new HashSet<>(incomeRecordsMap.keySet());
        dateKeys.addAll(outcomeRecordsMap.keySet());

        Map<String, Pair<Integer, Integer>> result = new TreeMap<>();
        for(String dateKey : dateKeys) {
            Integer incomesSum = incomeRecordsMap.getOrDefault(dateKey, new ArrayList<>()).stream()
                    .map(Record::getSum)
                    .reduce(Integer::sum)
                    .orElse(0);
            Integer outcomesSum = outcomeRecordsMap.getOrDefault(dateKey, new ArrayList<>()).stream()
                    .map(Record::getSum)
                    .reduce(Integer::sum)
                    .orElse(0) * -1;
            result.put(dateKey, Pair.of(incomesSum, outcomesSum));
        }
        return result;
    }
}
