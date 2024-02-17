package com.sewerynstawiarski.GitHubApiWebClient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositoryNoBranches {
    private String name;
    private OwnerDTO owner;
}
