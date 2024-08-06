package com.sewerynstawiarski.GitHubApiWebClient.services.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.sewerynstawiarski.GitHubApiWebClient.model.BranchDTO;
import com.sewerynstawiarski.GitHubApiWebClient.model.CommitDTO;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryDTO;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryNoBranchesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class GitHubClientIntegrationTest {
    @Autowired
    private WebClient webClient;
    @Value("${wiremock.server.port}")
    private int wireMockPort;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void init() {
        WireMock.reset();
    }

    @Test
    void listRepositoriesWithWireMockTest() {
        stubFor(WireMock.get(urlEqualTo("/user/octocat/repositories"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"name":"octocat.github.io","owner":{"login":"octocat"}}""")));

        var response = webClient.get()
                .uri("/user/octocat/repositories")
                .retrieve()
                .bodyToMono(RepositoryNoBranchesDTO.class)
                .map(this::addBranches)
                .block();

        assertNotNull(response);
        assertEquals("octocat.github.io", response.repository().name());
        assertEquals("octocat", response.repository().owner().login());
        assertEquals("Test", response.branches().get(0).name());
        assertEquals("123456", response.branches().get(0).commit().sha());

    }

    public RepositoryDTO addBranches(RepositoryNoBranchesDTO repositoryNoBranchesDTO) {
        BranchDTO branchDTO = BranchDTO.builder()
                .name("Test")
                .commit(CommitDTO.builder()
                        .sha("123456")
                        .build())
                .build();
        List<BranchDTO> branches = new ArrayList<>();
        branches.add(branchDTO);


        return RepositoryDTO.builder().repository(repositoryNoBranchesDTO).branches(branches).build();
    }

    @Test
    void listRepositoriesUserNotFoundWithWireMocKTest() {
        stubFor(WireMock.get(urlEqualTo("/user/BadUser/repositories"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"message":"Not Found","documentation_url":"https://docs.github.com/rest/repos/repos#list-repositories-for-a-user","status":"404"}""")));

        assertThrows(WebClientResponseException.class, () -> {
            webClient.get()
                    .uri("/user/BadUser/repositories")
                    .retrieve()
                    .bodyToMono(RepositoryNoBranchesDTO.class)
                    .block();

        });
    }

    @Test
    void listRepositoriesUserNotFoundWithWireMocKAndWebTestClientTest() {
        stubFor(WireMock.get(urlEqualTo("/users/BadUser/repos?type=owner"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"message":"Not Found","documentation_url":"https://docs.github.com/rest/repos/repos#list-repositories-for-a-user","status":"404"}""")));

        webTestClient.get().uri("http://localhost:" + wireMockPort + "/user/BadUser/repositories")
                .header("Accept", "application/json")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.message").isEqualTo("A user with this name was not found on GitHub");
    }

    @Test
    void listRepositoriesWithWireMocKAndWebClientTestTest() {
        stubFor(WireMock.get(urlEqualTo("/users/octocat/repos?type=owner"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [{"name":"octocat.github.io","owner":{"login":"octocat"},"fork":false}]""")));

        stubFor(WireMock.get(urlEqualTo("/repos/octocat/octocat.github.io/branches"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [{"name":"gh-pages","commit":{"sha":"c0e4a095428f36b81f0bd4239d353f71918cbef3"}}]""")));

        webTestClient.get().uri("http://localhost:" + wireMockPort + "/user/octocat/repositories")
                .header("Accept", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody()
                .jsonPath("$.size()").isEqualTo(1)
                .jsonPath("$[0].repository.name").isEqualTo("octocat.github.io")
                .jsonPath("$[0].repository.owner.login").isEqualTo("octocat")
                .jsonPath("$[0].branches[0].name").isEqualTo("gh-pages")
                .jsonPath("$[0].branches[0].commit.sha").isEqualTo("c0e4a095428f36b81f0bd4239d353f71918cbef3");
    }
}