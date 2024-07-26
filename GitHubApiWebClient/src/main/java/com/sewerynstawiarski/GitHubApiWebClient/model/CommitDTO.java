package com.sewerynstawiarski.GitHubApiWebClient.model;

import lombok.Builder;

@Builder
public record CommitDTO(String sha) {}
