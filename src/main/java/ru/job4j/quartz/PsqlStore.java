package ru.job4j.quartz;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private final Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("store.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            PsqlStore psqlStore = new PsqlStore(properties);
            psqlStore.getAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Post post) {
         try (PreparedStatement statement =
                 cnn.prepareStatement("insert into post(title, link, description, created_date) values (?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS)) {
             statement.setString(1, post.getTitle());
             statement.setString(2, post.getLink());
             statement.setString(3, post.getDescription());
             statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
             statement.execute();
             try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                 if (generatedKeys.next()) {
                     post.setId(generatedKeys.getInt(1));
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement =
                     cnn.prepareStatement("select * from post")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(newPost(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement statement =
                cnn.prepareStatement("select * from post where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    post = newPost(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public Post newPost(ResultSet resultSet) throws SQLException {
        return new Post(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("link"),
                resultSet.getString("description"),
                resultSet.getTimestamp("created_date").toLocalDateTime()
        );
    }
}
