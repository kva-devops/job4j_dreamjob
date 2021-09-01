package ru.job4j.dream.store;

import ru.job4j.dream.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();

    private static final AtomicInteger POST_ID = new AtomicInteger(0);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(0);
    private static final AtomicInteger USER_ID = new AtomicInteger(0);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private final Map<Integer, City> cities = new ConcurrentHashMap<>();

    private final Map<Integer, Email> emails = new ConcurrentHashMap<>();

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
        return users.values();
    }

    @Override
    public Collection<City> findAllCity() {
        return cities.values();
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public void saveUser(User user) {
        if (user.getId() == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    @Override
    public Post findById(int id) {
        return posts.get(id);
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
        return users.get(id);
    }

    @Override
    public void deletePost(int id) {
        Post post = findByIdPost(id);
        posts.remove(post);
    }

    @Override
    public void deleteCandidate(int id) {
        Candidate candidate = findByIdCandidate(id);
        candidates.remove(candidate);
    }

    @Override
    public void deleteUser(int id) {
        User user = findByIdUser(id);
        users.remove(user);
    }

    @Override
    public User findByEmail(String email) {
        User result = null;
        for (Map.Entry user : users.entrySet()) {
            User buff = (User) user.getValue();
            if (buff.getEmail().equals(email)) {
                result = buff;
            }
        }
        return result;
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
