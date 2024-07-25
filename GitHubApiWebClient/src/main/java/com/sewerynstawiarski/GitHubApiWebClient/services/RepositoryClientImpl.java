package com.sewerynstawiarski.GitHubApiWebClient.services;

import com.sewerynstawiarski.GitHubApiWebClient.model.BranchDTO;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryDTO;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryNoBranchesDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RepositoryClientImpl implements RepositoryClient {

    public static final String USER_REPOS = "users/{user}/repos";
    public static final String USER_REPO_BRANCHES = "repos/{user}/{repoName}/branches";
    private final WebClient webClient;

    public RepositoryClientImpl(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    @Override
    public Flux<RepositoryDTO> listRepositories(String user) {
        return webClient.get().uri(uriBuilder ->
                        uriBuilder.path(USER_REPOS)
                                .queryParam("type", "owner")
                                .build(user))
                .retrieve()
                .bodyToFlux(RepositoryNoBranchesDTO.class)
                .filter(repo -> !repo.fork())
                .flatMap(this::addBranches);
    }
    private Mono<RepositoryDTO> addBranches(RepositoryNoBranchesDTO repositoryNoBranches) {
        return getBranches(repositoryNoBranches.owner().login(), repositoryNoBranches.name())
                .map(branches -> RepositoryDTO.builder()
                        .repository(repositoryNoBranches)
                        .branches(branches)
                        .build());
    }

    @Override
    public Mono<List<BranchDTO>> getBranches(String user, String repoName) {


        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(USER_REPO_BRANCHES).build(user, repoName))
                .retrieve()
                .bodyToFlux(BranchDTO.class)
                .collectList();
    }

}
