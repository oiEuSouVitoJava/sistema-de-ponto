// src/main/java/com/vito/backend/service/UsuarioService.java
package com.vito.backend.service;

import com.vito.backend.DTO.UsuarioDTO;
import com.vito.backend.DTO.UsuarioCadastroDTO;
import com.vito.backend.DTO.UsuarioRespostaDTO;
import com.vito.backend.model.Usuario;
import com.vito.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioDTO(u.getId(), u.getNome(), u.getEmail()))
                .collect(Collectors.toList());
    }

    public UsuarioRespostaDTO criarUsuario(UsuarioCadastroDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("E-mail já está em uso!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioRespostaDTO(salvo.getId(), salvo.getNome(), salvo.getEmail());
    }

}
