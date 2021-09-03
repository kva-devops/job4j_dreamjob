package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM post ORDER BY id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(
                            it.getInt("id"),
                            it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in FIND ALL POSTS method", e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM candidate ORDER BY id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("city_id")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in FIND ALL CANDIDATES method", e);
        }
        return candidates;
    }

    @Override
    public Collection<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users ORDER BY id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in FIND ALL USERS method", e);
        }
        return users;
    }

    @Override
    public Collection<City> findAllCity() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM city ORDER BY id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(new City(
                            it.getInt("id"),
                            it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in FIND ALL CITY method", e);
        }
        return cities;
    }

    @Override
    public Collection<Post> findLastPosts() {
        List<Post> lastPosts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM post "
                      + "WHERE add_time "
                      + "BETWEEN CURRENT_TIMESTAMP - INTERVAL '1 DAY' "
                      + "AND CURRENT_TIMESTAMP "
                      + "ORDER BY id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    lastPosts.add(new Post(
                            it.getInt("id"),
                            it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in FIND LAST POSTS method", e);
        }
        return lastPosts;
    }

    @Override
    public Collection<Candidate> findLastCandidates() {
        List<Candidate> lastCandidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM candidate "
                      + "WHERE add_time "
                      + "BETWEEN CURRENT_TIMESTAMP - INTERVAL '1 DAY' "
                      + "AND CURRENT_TIMESTAMP "
                      + "ORDER BY id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    lastCandidates.add(new Candidate(
                                              it.getInt("id"),
                                              it.getString("name"),
                                              it.getInt("city_id")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in FIND LAST CANDIDATES method", e);
        }
        return lastCandidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    @Override
    public void saveUser(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    private User create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in USER CREATE method", e);
        }
        return user;
    }

    private void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE users SET name=?, email=?, password=? WHERE id=?")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in USER UPDATE method", e);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO post(name, add_time) VALUES (?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setTimestamp(2, post.getCreated());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in POST CREATE method", e);
        }
        return post;
    }

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE post SET name=? WHERE id=?")) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in POST UPDATE method", e);
        }
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidate(name, add_time, city_id) VALUES(?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setTimestamp(2, candidate.getCreated());
            ps.setInt(3, candidate.getCityId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in CANDIDATE CREATE method", e);
        }
        return candidate;
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE candidate SET name=?, city_id=? WHERE id=?")) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setInt(3, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in CANDIDATE UPDATE method", e);
        }
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM post WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    post = new Post(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in POST FIND BY ID method", e);
        }
        return post;
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM candidate WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    candidate = new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("city_id"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in CANDIDATE FIND BY ID method", e);
        }
        return candidate;
    }

    @Override
    public User findByIdUser(int id) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    user = new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in USER FIND BY ID method", e);
        }
        return user;
    }

    @Override
    public void deletePost(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM post WHERE id=?")) {
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in DELETE POST method.", e);
        }
    }

    @Override
    public void deleteCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM candidate WHERE id=?")) {
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in DELETE CANDIDATE method.", e);
        }
        String path = "../images/" + id;
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            LOG.error("Exception IO in DELETE CANDIDATE method.", e);
        }
    }

    @Override
    public void deleteUser(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM users WHERE id=?")) {
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in DELETE USER method", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email=?")) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    user = new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in USER FIND BY EMAIL method", e);
        }
        return user;
    }
}
