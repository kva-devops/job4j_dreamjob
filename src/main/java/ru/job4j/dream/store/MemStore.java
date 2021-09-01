package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();

    private static final AtomicInteger POST_ID = new AtomicInteger(4);

    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemStore() {
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public Collection<User> findAllUsers() {
        return null;
    }

    @Override
    public Collection<City> findAllCity() {
        return null;
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public void saveCandidate(Candidate candidate) {

    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public Post findById(int id) {
        return null;
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public Post findByIdPost(int id) {
        return posts.get(id);
    }

    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    @Override
    public User findByIdUser(int id) {
        return null;
    }

    @Override
    public void deletePost(int id) {

    }

    @Override
    public void deleteCandidate(int id) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    public void delCandidate(int id) {
        candidates.remove(id);
        String path = "../images/" + id;
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
