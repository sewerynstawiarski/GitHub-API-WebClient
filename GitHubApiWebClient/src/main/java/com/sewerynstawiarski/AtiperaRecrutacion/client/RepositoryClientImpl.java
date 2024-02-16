package com.sewerynstawiarski.AtiperaRecrutacion.client;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sewerynstawiarski.AtiperaRecrutacion.model.BranchDTO;
import com.sewerynstawiarski.AtiperaRecrutacion.model.RepositoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepositoryClientImpl implements RepositoryClient {

    private final WebClient webClient;

    public RepositoryClientImpl(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    @Override
    public List<RepositoryDTO> listRepositories(String user) {
        var repositories = webClient.get().uri(uriBuilder ->
                        uriBuilder.path("users/{user}/repos")
                                .queryParam("type", "owner")
                                .build(user))
                .retrieve()
                .bodyToFlux(RepositoryDTO.class)
                .collect(Collectors.toList()).block();

        assert repositories != null;
        var repoWithBranches = repositories.stream()
                .peek(repositoryDTO -> repositoryDTO
                        .setBranches(getBranches(repositoryDTO.getOwner().getLogin(), repositoryDTO.getName())
                                .collect(Collectors.toList())
                                .block()))
                .toList();

        return repositories;
    }

    @Override
    public Flux<BranchDTO> getBranches(String user, String repoName) {
        var branch = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("repos/{user}/{repoName}/branches").build(user, repoName))
                .retrieve()
                .bodyToFlux(BranchDTO.class);


        return branch;
    }
}
