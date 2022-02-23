package ru.job4j.gc;

public class User {
    
    private int age;
    private String name;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public static void main(String[] args) {
        User user1 = new User(1, "Tom");
        User user2 = new User(1, "Tom");
        User user3 = new User(1, "Tom");
        User user4 = new User(1, "Tom");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.printf("Removed %d %s%n", age, name);
    }
}
