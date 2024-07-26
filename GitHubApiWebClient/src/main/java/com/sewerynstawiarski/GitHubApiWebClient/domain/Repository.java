package com.sewerynstawiarski.GitHubApiWebClient.domain;

import lombok.Builder;

import java.util.List;
@Builder
public record Repository(RepositoryNoBranches repository, List<Branch> branches ) {}
