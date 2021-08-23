package ru.job4j;

public class Candidate {
    int id;
    String name;
    Resume resume;

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void publishResume(Resume resume) {
        this.resume = resume;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Resume getResume() {
        return resume;
    }
}
