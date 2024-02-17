package com.sewerynstawiarski.GitHubApiWebClient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositoryDTO {
    private RepositoryNoBranches repository;
    private List<BranchDTO> branches;
}
