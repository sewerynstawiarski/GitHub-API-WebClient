package com.sewerynstawiarski.GitHubApiWebClient.controllers;

import com.sewerynstawiarski.GitHubApiWebClient.client.RepositoryClient;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RepositoryController {

    public final RepositoryClient repoClient;

    @GetMapping("/user/{username}/repositories")
    Flux<RepositoryDTO> getUsersRepositories(@RequestHeader(HttpHeaders.ACCEPT) @PathVariable String username) {
        return repoClient.listRepositories(username);
    }
}
