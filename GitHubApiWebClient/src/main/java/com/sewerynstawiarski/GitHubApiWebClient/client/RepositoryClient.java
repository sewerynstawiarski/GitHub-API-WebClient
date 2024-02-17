package com.sewerynstawiarski.GitHubApiWebClient.client;

import com.sewerynstawiarski.GitHubApiWebClient.model.BranchDTO;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RepositoryClient {
    Flux<RepositoryDTO> listRepositories(String user);

    Mono<List<BranchDTO>> getBranches(String user, String repoName);
}
