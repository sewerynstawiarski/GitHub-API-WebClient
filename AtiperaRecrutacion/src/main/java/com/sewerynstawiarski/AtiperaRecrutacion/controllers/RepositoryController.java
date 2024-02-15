package com.sewerynstawiarski.AtiperaRecrutacion.controllers;

import com.sewerynstawiarski.AtiperaRecrutacion.client.RepositoryClient;
import com.sewerynstawiarski.AtiperaRecrutacion.model.RepositoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RepositoryController {

   public  final RepositoryClient repoClient;
   @GetMapping("/repositories/{username}")
   List<RepositoryDTO> getUsersRepositories(@PathVariable String username) {
    var repositories = repoClient.listRepositories(username)
            .collect(Collectors.toList()).block();

       assert repositories != null;
       var repoWithBranches =  repositories.stream()
            .peek(repositoryDTO -> repositoryDTO.setBranches(repoClient.getBranches(repositoryDTO.getName(), repositoryDTO.getOwner().getLogin())
                    .collect(Collectors.toList())
                    .block()))
               .toList();

    return  repoWithBranches;

   }

}
