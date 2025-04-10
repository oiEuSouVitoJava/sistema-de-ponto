package com.vito.backend.controller;

import com.vito.backend.model.Meta;
import com.vito.backend.model.Usuario;
import com.vito.backend.repository.MetaRepository;
import com.vito.backend.repository.UsuarioRepository;
import com.vito.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/metas")
public class MetaController {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ LISTAR TODAS AS METAS DE TODOS OS USUÁRIOS
    @GetMapping
    public List<Meta> listarTodasMetas() {
        return metaRepository.findAll();
    }

    // ✅ ATUALIZAR STATUS (apenas se a meta for do usuário autenticado)
    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatusMeta(@PathVariable Long id, @RequestBody Map<String, Boolean> body,
                                                 @RequestHeader("Authorization") String authHeader) {
        Optional<Meta> metaOpt = metaRepository.findById(id);
        if (metaOpt.isEmpty()) return ResponseEntity.notFound().build();

        Meta meta = metaOpt.get();

        // Extrair usuário autenticado a partir do token
        String email = jwtUtil.extractUsername(authHeader.replace("Bearer ", ""));
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        // Verifica se a meta pertence ao usuário
        if (usuario == null || !meta.getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.status(403).body("Você não tem permissão para alterar esta meta.");
        }

        // Atualizar o status
        meta.setAlvoAlcancado(body.get("alvoAlcancado"));
        metaRepository.save(meta);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> criarMeta(@RequestBody Meta meta, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        meta.setUsuario(usuario);
        meta.setAlvoAlcancado(false); // Por padrão, meta inicia não alcançada

        Meta salva = metaRepository.save(meta);

        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

}
