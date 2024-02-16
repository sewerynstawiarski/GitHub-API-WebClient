package com.sewerynstawiarski.AtiperaRecrutacion.controllers;

import com.sewerynstawiarski.AtiperaRecrutacion.client.RepositoryClient;
import com.sewerynstawiarski.AtiperaRecrutacion.model.RepositoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RepositoryController {

    public final RepositoryClient repoClient;

    @GetMapping("/repositories/{username}")
    List<RepositoryDTO> getUsersRepositories(@RequestHeader("Accept: application/json") @PathVariable String username) {
        return repoClient.listRepositories(username);
    }
}
