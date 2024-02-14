package com.sewerynstawiarski.AtiperaRecrutacion.client;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sewerynstawiarski.AtiperaRecrutacion.model.BranchDTO;
import com.sewerynstawiarski.AtiperaRecrutacion.model.RepositoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RepositoryClientImpl implements RepositoryClient {

    private final WebClient webClient;

    public RepositoryClientImpl(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    @Override
    public Flux<RepositoryDTO> listRepositories(String user) {
        var repositories =  webClient.get().uri(uriBuilder ->
                        uriBuilder.path("users/{user}/repos")
                                .queryParam("type", "owner")
                                .build(user))
                .retrieve()
                .bodyToFlux(RepositoryDTO.class);
    }

    @Override
    public Mono<BranchDTO> getBranches(String repoName) {
        var branch = webClient.get()
                .uri("")


        return ;
    }
}
