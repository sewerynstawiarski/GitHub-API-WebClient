package com.sewerynstawiarski.GitHubApiWebClient.domain;

import lombok.Builder;

@Builder
public record Commit(String sha) {}
