package com.sewerynstawiarski.GitHubApiWebClient.services.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryNoBranchesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port =0)
class RepositoryClientImplIntTest {
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Value("${wiremock.server.port}")
    private int wireMockPort;

    @BeforeEach
    void init() {
        WireMock.reset();
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
                .bodyToMono(RepositoryNoBranchesDTO.class)
                .block();

        System.out.println(response);

        assertNotNull(response);
        assertEquals("octocat.github.io", response.name());
        assertEquals("octocat", response.owner().login());

    }
}