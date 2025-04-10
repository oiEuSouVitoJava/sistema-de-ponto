package com.vito.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MetaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Long usuarioId;

}
