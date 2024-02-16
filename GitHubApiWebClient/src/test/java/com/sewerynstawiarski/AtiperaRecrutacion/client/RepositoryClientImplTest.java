package com.sewerynstawiarski.AtiperaRecrutacion.client;


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

        webClient.listRepositories("sewerynstawiarski")
                .forEach(repo -> {
                    System.out.println(repo.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);

    }

    @Test
    void testGetBranches() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        var branches = webClient.getBranches("sewerynstawiarski", "Calculator")
                .subscribe(branchDTO -> {
                    System.out.println(branchDTO.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }
}