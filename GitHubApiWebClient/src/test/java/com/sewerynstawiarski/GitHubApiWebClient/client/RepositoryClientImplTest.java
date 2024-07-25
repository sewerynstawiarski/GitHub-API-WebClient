package com.sewerynstawiarski.GitHubApiWebClient.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.sewerynstawiarski.GitHubApiWebClient.controllers.GlobalExceptionHandler;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryDTO;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryNoBranches;
import net.minidev.json.JSONValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import wiremock.org.eclipse.jetty.util.ajax.JSON;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port =0)
class RepositoryClientImplTest {
    @Autowired
    private RepositoryClient webClient;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Value("${wiremock.server.port}")
    private int wireMockPort;

    public static final String USER_REPOSITORIES = "http://localhost:8080/user/{USERNAME}/repositories";

    @BeforeEach
    void init() {
        WireMock.reset();
    }

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

    @Test
    void testListRepositories() {
        webTestClient.get().uri(USER_REPOSITORIES, "octocat")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(8);
    }
    @Test
    void testListRepositoriesNotFound() {
        webTestClient.get().uri(USER_REPOSITORIES, "dfihgbfodfuihbgndbn")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.message").isEqualTo("User of this name was not found on GitHub");
    }
    @Test
    void testListRepositoriesWithWireMock() {
        stubFor(WireMock.get(urlEqualTo("/user/octocat/repositories"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"name":"octocat.github.io","owner":{"login":"octocat"}}""")));

        WebClient webClient = webClientBuilder.baseUrl("http://localhost:" + wireMockPort).defaultHeader("Accept", "application/vnd.github+json").build();

        var response = webClient.get()
                .uri("/user/octocat/repositories")
                .retrieve()
                .bodyToMono(RepositoryNoBranches.class)
                .block();

        System.out.println(response);

        assertNotNull(response);
        assertEquals("octocat.github.io", response.name());
        assertEquals("octocat", response.owner().login());

    }
}