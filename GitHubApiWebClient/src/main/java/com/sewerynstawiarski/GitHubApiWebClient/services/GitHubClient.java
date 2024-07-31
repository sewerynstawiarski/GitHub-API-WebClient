package com.sewerynstawiarski.GitHubApiWebClient.services;

import com.sewerynstawiarski.GitHubApiWebClient.domain.Branch;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface GitHubClient {
    Flux<RepositoryDTO> listRepositories(String user);

    Mono<List<Branch>> getBranches(String user, String repoName);
}
