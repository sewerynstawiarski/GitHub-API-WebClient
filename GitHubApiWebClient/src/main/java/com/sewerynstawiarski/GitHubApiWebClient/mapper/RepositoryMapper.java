package com.sewerynstawiarski.GitHubApiWebClient.mapper;

import com.sewerynstawiarski.GitHubApiWebClient.domain.Repository;
import com.sewerynstawiarski.GitHubApiWebClient.model.RepositoryDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RepositoryMapper {
RepositoryDTO repositoryToRepositoryDTO(Repository repository);
Repository respositoryDTOToRepository (RepositoryDTO repositoryDTO);
}
