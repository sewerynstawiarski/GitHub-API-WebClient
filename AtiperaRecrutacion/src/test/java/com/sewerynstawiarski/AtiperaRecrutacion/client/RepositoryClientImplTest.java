package com.sewerynstawiarski.AtiperaRecrutacion.client;

import com.sewerynstawiarski.AtiperaRecrutacion.model.RepositoryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RepositoryClientImplTest {
    @Autowired
    RepositoryClient webClient;

    @Test
    void testGetRepositories() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        webClient.listRepositories("sewerynstawiarski")
                .subscribe(repo -> {
                    System.out.println(repo.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);

    }
}