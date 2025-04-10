package com.vito.backend.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Data
public class MarcacaoDTO {
    private Long id;
    private Long usuarioId;
    private Long metaId;
    private LocalDateTime dataHora;
    private String observacao;
}
