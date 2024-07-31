package com.sewerynstawiarski.GitHubApiWebClient.services.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class GitHubClientImplementationTest {
    @Autowired
    private WebTestClient webTestClient;
    public static final String USER_REPOSITORIES = "http://localhost:8080/user/{USERNAME}/repositories";

    @Test
    void testListRepositories() {
        webTestClient.get().uri(USER_REPOSITORIES, "octocat")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(6);
    }

    @Test
    void testListRepositoriesNotFound() {
        webTestClient.get().uri(USER_REPOSITORIES, "dfihgbfodfuihbgndbn")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.message").isEqualTo("A user with this name was not found on GitHub");
    }
}