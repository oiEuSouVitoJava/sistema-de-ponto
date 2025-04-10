package com.vito.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MarcacaoDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private Long metaId;
    private String metaNome;
    private String metaDescricao;
    private LocalDateTime dataHora;
    private String observacao;
}
