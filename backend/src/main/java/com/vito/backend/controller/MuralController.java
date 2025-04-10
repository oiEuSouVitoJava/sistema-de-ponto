// src/main/java/com/vito/backend/controller/MuralController.java
package com.vito.backend.controller;

import com.vito.backend.model.MensagemMural;
import com.vito.backend.model.Usuario;
import com.vito.backend.repository.MensagemMuralRepository;
import com.vito.backend.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mural")
public class MuralController {

    @Autowired
    private MensagemMuralRepository muralRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<MensagemMural> listarMensagens() {
        return muralRepository.findAll();
    }

    @PostMapping
    public MensagemMural postarMensagem(@RequestBody String conteudo, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        MensagemMural mensagem = new MensagemMural();
        mensagem.setConteudo(conteudo);
        mensagem.setDataHora(LocalDateTime.now());
        mensagem.setUsuario(usuario);

        return muralRepository.save(mensagem);
    }
}
