package ru.job4j.quartz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PartsLoading {

    public static void main(String[] args) throws Exception {
        String link = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        searchData(link);
    }

    public static Post searchData(String link) throws Exception {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        Document doc = Jsoup.connect(link).get();
        String description  = doc.select(".msgBody").get(1).text();
        String date = doc.select(".msgFooter").get(0).text().substring(0, 16).trim();
        String title = doc.select(".messageHeader").get(0).text();
        return new Post(title, link, description, parser.parse(date));
    }
}
