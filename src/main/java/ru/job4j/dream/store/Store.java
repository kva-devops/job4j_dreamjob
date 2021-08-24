package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.time.*;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static final Store INST = new Store();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1,
                "Junior Java Job",
                "Junior position description",
                LocalDate.parse("2021-07-22")));
        posts.put(2, new Post(2,
                "Middle Java Job",
                "Middle position description",
                LocalDate.parse("2021-07-22")));
        posts.put(3, new Post(3,
                "Senior Java Job",
                "Senior position description",
                LocalDate.parse("2021-07-22")));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
