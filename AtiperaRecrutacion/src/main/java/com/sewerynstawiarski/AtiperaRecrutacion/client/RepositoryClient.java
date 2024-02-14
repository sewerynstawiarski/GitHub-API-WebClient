package com.sewerynstawiarski.AtiperaRecrutacion.client;

import com.sewerynstawiarski.AtiperaRecrutacion.model.BranchDTO;
import com.sewerynstawiarski.AtiperaRecrutacion.model.RepositoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RepositoryClient {
    Flux<RepositoryDTO> listRepositories(String user);
    Mono<BranchDTO> getBranches(String repoName);
}
