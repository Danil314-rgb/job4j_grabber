package ru.job4j.quartz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public static void main(String[] args) throws Exception{
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            Element parent = td.parent();
            String linkVacancy = href.attr("href");
            System.out.println(linkVacancy);
            /*System.out.println(href.text());
            System.out.println(parent.children().get(5).text());*/
            /*SqlRuParse ruParse = new SqlRuParse();
            ruParse.list(linkVacancy);*/
        }
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        posts.add(detail(link));
        return posts;
    }

    @Override
    public Post detail(String link) {
        Post post = null;
        try {
            PartsLoading parts = new PartsLoading();
            post = parts.searchData(link);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }
}
