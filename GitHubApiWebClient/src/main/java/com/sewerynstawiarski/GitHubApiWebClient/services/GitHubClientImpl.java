package com.sewerynstawiarski.GitHubApiWebClient.services;

import com.sewerynstawiarski.GitHubApiWebClient.domain.Branch;
import com.sewerynstawiarski.GitHubApiWebClient.domain.Repository;
import com.sewerynstawiarski.GitHubApiWebClient.domain.RepositoryNoBranches;
import com.sewerynstawiarski.GitHubApiWebClient.mapper.RepositoryMapper;
import com.sewerynstawiarski.GitHubApiWebClient.mapper.RepositoryMapperImpl;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public final class GitHubClientImpl implements GitHubClient {

    public static final String USER_REPOS = "users/{user}/repos";
    public static final String USER_REPO_BRANCHES = "repos/{user}/{repoName}/branches";
    private final WebClient webClient;
    private final RepositoryMapper repositoryMapper;

    public GitHubClientImpl(WebClient.Builder webClient, RepositoryMapper repositoryMapper) {
        this.webClient = webClient.build();
        this.repositoryMapper = new RepositoryMapperImpl();
    }

    @Override
    public Flux<RepositoryDTO> listRepositories(String user) {
        return webClient.get().uri(uriBuilder ->
                        uriBuilder.path(USER_REPOS)
                                .queryParam("type", "owner")
                                .build(user))
                .retrieve()
                .bodyToFlux(RepositoryNoBranches.class)
                .filter(repo -> !repo.fork())
                .flatMap(this::addBranches)
                .map(repositoryMapper::repositoryToRepositoryDTO);
    }
    private Mono<Repository> addBranches(RepositoryNoBranches repositoryNoBranches) {
        return getBranches(repositoryNoBranches.owner().login(), repositoryNoBranches.name())
                .map(branches -> Repository.builder()
                        .repository(repositoryNoBranches)
                        .branches(branches)
                        .build());
    }

    @Override
    public Mono<List<Branch>> getBranches(String user, String repoName) {


        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(USER_REPO_BRANCHES).build(user, repoName))
                .retrieve()
                .bodyToFlux(Branch.class)
                .collectList();
    }

}
