package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Collection<User> findAllUsers();

    void save(Post post);

    void saveCandidate(Candidate candidate);

    void saveUser(User user);

    Post findById(int id);

    Candidate findByIdCandidate(int id);

    User findByIdUser(int id);

    void deletePost(int id);

    void deleteCandidate(int id);

    void deleteUser(int id);
}
