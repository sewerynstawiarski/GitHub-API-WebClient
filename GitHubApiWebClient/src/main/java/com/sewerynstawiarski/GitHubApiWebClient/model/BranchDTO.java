package com.sewerynstawiarski.GitHubApiWebClient.model;

import lombok.Builder;

@Builder
public record BranchDTO(String name, CommitDTO commit) {}
