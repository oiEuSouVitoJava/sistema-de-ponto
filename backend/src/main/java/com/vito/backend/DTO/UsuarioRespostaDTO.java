// src/main/java/com/vito/backend/DTO/UsuarioRespostaDTO.java
package com.vito.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioRespostaDTO {
    private Long id;
    private String nome;
    private String email;
}
