package ru.job4j.quartz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final LocalDate PRESENTDAY = LocalDate.now();
    private static final DateTimeFormatter FORMATTERFORPRESENTDAY = DateTimeFormatter.ofPattern("d MM yy");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d MM yy, H:mm");
    private static final String TODAY = "сегодня";
    private static final String YESTERDAY = "вчера";

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "01"),
            Map.entry("фев", "02"),
            Map.entry("мар", "03"),
            Map.entry("апр", "04"),
            Map.entry("май", "05"),
            Map.entry("июн", "06"),
            Map.entry("июл", "07"),
            Map.entry("авг", "08"),
            Map.entry("сен", "09"),
            Map.entry("отк", "10"),
            Map.entry("ноя", "11"),
            Map.entry("дек", "12")
    );

    @Override
    public LocalDateTime parse(String parse) {
        String result = "";
        String[] all = parse.split(",");
        String[] oneStr = all[0].split(" ");
        if (oneStr.length < 2) {
            if (TODAY.contains(oneStr[0])) {
                result = parse.replaceFirst(oneStr[0], PRESENTDAY.format(FORMATTERFORPRESENTDAY));
            } else if (YESTERDAY.contains(oneStr[0])) {
                result = parse.replaceFirst(oneStr[0], PRESENTDAY.minusDays(1).format(FORMATTERFORPRESENTDAY));
            }
        } else {
            String moth = oneStr[1];
            result = parse.replaceFirst(moth, MONTHS.get(moth));
        }
        return LocalDateTime.parse(result, FORMATTER);
    }

    public static void main(String[] args) throws Exception {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element parent = td.parent();
            String str = parent.children().get(5).text();
            System.out.println(parser.parse(str));
        }
    }
}
