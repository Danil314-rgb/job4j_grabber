package ru.job4j.quartz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PartsLoading {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t").get();
        Elements row = doc.select(".msgBody");
        Elements time = doc.select(".msgFooter");
        /*for (Element r : row) {
            Element ro = r.parent().child(1);
            System.out.println(ro.text());
            System.out.println("-------------");
        }*/
        var ro = row.parents().text();
        System.out.println(ro);
        System.out.println("-------------");
        for (Element d : time) {
            Element date = d.parent().child(0);
            System.out.println(date.text());
            System.out.println("------------");
        }
    }
}
