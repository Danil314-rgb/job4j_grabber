package ru.job4j.quartz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    /*private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }*/

    public static void main(String[] args) {
        String link = "https://www.sql.ru/forum/job-offers/";
        SqlRuParse ruParse = new SqlRuParse();
        ruParse.list(link);
    }

    @Override
    public List<Post> list(String link)  {
        List<Post> posts = new ArrayList<>();
        try {
            for (int i = 1; i < 6; i++) {
                Document doc = Jsoup.connect(link + i).get();
                Elements row = doc.select(".postslisttopic");
                for (Element td : row) {
                    String namePost = td.text();
                    String linkPost = td.child(0).attr("href");
                    if (namePost.contains("Java") && !namePost.contains("JavaScript")) {
                        /*System.out.println(linkPost);
                        System.out.println(namePost);*/
                        posts.add(detail(linkPost));
                    }
                }
                /*System.out.println("******************");*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post detail(String link) {
        Post post = null;
        try {
            Document doc = Jsoup.connect(link).get();
            String description  = doc.select(".msgBody").get(1).text();
            String date = doc.select(".msgFooter").get(0).text();
            date = date.substring(0, date.indexOf('[')).trim();
            String title = doc.select(".messageHeader").get(0).text();

            SqlRuDateTimeParser dateTimeParser = new SqlRuDateTimeParser();
            LocalDateTime created = dateTimeParser.parse(date);
            post = new Post(title, link, description, created);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }
}
