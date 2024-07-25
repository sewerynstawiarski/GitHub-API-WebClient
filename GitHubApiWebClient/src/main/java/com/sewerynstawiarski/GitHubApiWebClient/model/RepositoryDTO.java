package com.sewerynstawiarski.GitHubApiWebClient.model;

import lombok.Builder;

import java.util.List;
@Builder
public record RepositoryDTO(RepositoryNoBranches repository, List<BranchDTO> branches ) {}
