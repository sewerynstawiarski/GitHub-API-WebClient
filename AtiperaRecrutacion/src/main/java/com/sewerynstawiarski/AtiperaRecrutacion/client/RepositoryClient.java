package com.sewerynstawiarski.AtiperaRecrutacion.client;

import com.sewerynstawiarski.AtiperaRecrutacion.model.BranchDTO;
import com.sewerynstawiarski.AtiperaRecrutacion.model.RepositoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RepositoryClient {
    Flux<RepositoryDTO> listRepositories(String user);
    Flux<BranchDTO> getBranches(String user, String repoName);
}
