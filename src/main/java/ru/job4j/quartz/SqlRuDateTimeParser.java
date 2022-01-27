package ru.job4j.quartz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "01"),
            Map.entry("фев", "02"),
            Map.entry("мар", "03"),
            Map.entry("апр", "04"),
            Map.entry("май", "05"),
            Map.entry("июн", "06"),
            Map.entry("июл", "07"),
            Map.entry("авг", "08"),
            Map.entry("сент", "09"),
            Map.entry("отк", "10"),
            Map.entry("нояб", "11"),
            Map.entry("дек", "12")
    );

    @Override
    public LocalDateTime parse(String parse) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm"); /*""w.MMM.yy H:mm*/
        LocalDateTime ldt = LocalDateTime.parse(parse, formatter);
        System.out.println(ldt);
        return null;
    }

    public static void main(String[] args) throws Exception {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        /*Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element parent = td.parent();
            String str = parent.children().get(5).text();
            LocalDateTime date =  parser.parse(str);
            System.out.println(date);
        }*/
        String str = "20-Dec-22 10:10:00";
        System.out.println(parser.parse(str));
    }
}
