package com.sewerynstawiarski.AtiperaRecrutacion.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchDTO {
    private String name;
    private CommitDTO commit;
}
