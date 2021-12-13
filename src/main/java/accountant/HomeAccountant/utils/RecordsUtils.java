package accountant.HomeAccountant.utils;

import accountant.HomeAccountant.models.Record;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecordsUtils {

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String EMPTY_SPACE = "\\h";
    public static final String TYPE_FILTER = "Наличные";

    public static List<Record> parseCsv(MultipartFile file) {
        String row = "";
        List<Record> result = new ArrayList<>();
        try(InputStream is = file.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            int count = 0;
            while ((row = reader.readLine()) != null) {
                if(count == 0) { //skip header
                    count++;
                    continue;
                }
                String[] columns = row.split(",");
                LocalDate date = LocalDate.parse(columns[0], DateTimeFormatter.ofPattern(DATE_PATTERN));
                int sum = Integer.parseInt(columns[3].replaceAll(EMPTY_SPACE, ""));
                if(columns[1].equals(TYPE_FILTER)) {
                    result.add(new Record(date, columns[1], columns[2], sum, columns[4], columns.length == 8 ? columns[7] : ""));
                }
            }
        } catch (IOException e) {
            //todo add logger
            //todo rethrow
            e.printStackTrace();
            result.clear();
        }
        return result;
    }
}
