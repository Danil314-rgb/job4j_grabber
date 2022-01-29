package ru.job4j.quartz;

import java.util.List;

public interface Parse {

    List<Post> list(String link) throws Exception;

    Post detail(String link)  throws Exception;
}
