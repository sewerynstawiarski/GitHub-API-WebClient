package com.sewerynstawiarski.AtiperaRecrutacion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositoryDTO {
    private String name;
    private OwnerDTO owner;
    List<BranchDTO> branches;
}
