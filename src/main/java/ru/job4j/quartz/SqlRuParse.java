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
        for (int i = 1; i < 6; i++) {
            String link = "https://www.sql.ru/forum/job-offers/" + i;
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            SqlRuParse ruParse = new SqlRuParse();
            List<Post> list = ruParse.list(link);
            /*for (Element td : row){
                Element href = td.child(0);
                Element parent = td.parent();
                String linkVacancy = href.attr("href");
                System.out.println(linkVacancy);
            System.out.println(href.text());
            System.out.println(parent.children().get(5).text());
            }*/
        }
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        /*Получить ссылку из href = ссылки на пост и её передать в detail*/
        posts.add(detail(link));
        return posts;
    }

    @Override
    public Post detail(String link) {
        Post post = null;
        try {
            SqlRuParse ruParse = new SqlRuParse();
            Document doc = Jsoup.connect(link).get();
            String description  = doc.select(".msgBody").get(1).text();
            String date = doc.select(".msgFooter").get(0).text();
            date = date.substring(0, date.indexOf('[')).trim();
            String title = doc.select(".messageHeader").get(0).text();

            post = Post(title, link, description, parser.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }
}
