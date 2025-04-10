package com.vito.backend.controller;

import com.vito.backend.DTO.UsuarioCadastroDTO;
import com.vito.backend.DTO.UsuarioDTO;
import com.vito.backend.DTO.UsuarioRespostaDTO;
import com.vito.backend.model.Usuario;
import com.vito.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public UsuarioRespostaDTO criarUsuario(@RequestBody UsuarioCadastroDTO dto) {
        return usuarioService.criarUsuario(dto);
    }



    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarTodos();
    }
}
