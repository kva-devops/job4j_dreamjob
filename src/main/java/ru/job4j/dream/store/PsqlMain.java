package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job"));
        store.saveCandidate(new Candidate(0, "Ivan"));
        System.out.println("Post table: ");
        for (Post post: store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        System.out.println("Candidate table:");
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        System.out.println("Update post:");
        store.save(new Post(1, "Update Java Job Junior"));
        for (Post post: store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        System.out.println("Update candidate:");
        store.saveCandidate(new Candidate(1, "Petr"));
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }

        System.out.println("Method findById post:");
        Post postResultFindById = store.findById(1);
        if (postResultFindById != null) {
            System.out.println(postResultFindById.getName());
        } else {
            System.out.println("not find post");
        }

        System.out.println("Method findById candidate:");
        Candidate candidateResultFindById = store.findByIdCandidate(1);
        if (candidateResultFindById != null) {
            System.out.println(candidateResultFindById.getName());
        } else {
            System.out.println("not find candidate");
        }

    }
}
