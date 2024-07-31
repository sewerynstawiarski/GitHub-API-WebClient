package com.sewerynstawiarski.GitHubApiWebClient.controllers;

import com.sewerynstawiarski.GitHubApiWebClient.services.GitHubClient;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public final class GitHubClientController {

    private final GitHubClient repoClient;

    @GetMapping("/user/{username}/repositories")
    Flux<RepositoryDTO> getUserRepositories(@RequestHeader(HttpHeaders.ACCEPT) @PathVariable String username) {
        return repoClient.listRepositories(username);
    }
}
