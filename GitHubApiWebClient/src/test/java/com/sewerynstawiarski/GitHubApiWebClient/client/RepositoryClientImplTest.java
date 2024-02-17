package com.sewerynstawiarski.GitHubApiWebClient.client;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class RepositoryClientImplTest {
    @Autowired
    RepositoryClient webClient;

    @Test
    void testGetRepositories() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        webClient.listRepositories("octocat")
                .subscribe(repo -> {
                    System.out.println(repo.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);

    }

    @Test
    void testGetBranches() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        var branches = webClient.getBranches("octocat", "git-consortium")
                .subscribe(branchDTO -> {
                    System.out.println(branchDTO.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }
}