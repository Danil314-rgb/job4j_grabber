package ru.job4j.quartz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PartsLoading {

    public static void main(String[] args) throws Exception {
        PartsLoading partsLoading = new PartsLoading();
        String link = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        partsLoading.searchData(link);
    }

    public Post searchData(String link) throws Exception {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        Document doc = Jsoup.connect(link).get();
        String description  = doc.select(".msgBody").get(1).text();
        String date = doc.select(".msgFooter").get(0).text();
        date = date.substring(0, date.indexOf('[')).trim();
        String title = doc.select(".messageHeader").get(0).text();
        return new Post(title, link, description, parser.parse(date));
    }
}
