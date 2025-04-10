package com.vito.backend.service;

import com.vito.backend.DTO.MetaDTO;
import com.vito.backend.model.Meta;
import com.vito.backend.model.Usuario;
import com.vito.backend.repository.MetaRepository;
import com.vito.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public MetaDTO criarMeta(MetaDTO metaDTO) {
        Meta meta = new Meta();
        meta.setNome(metaDTO.getNome());
        meta.setDescricao(metaDTO.getDescricao());

        Usuario usuario = usuarioRepository.findById(metaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        meta.setUsuario(usuario);

        Meta metaSalva = metaRepository.save(meta);
        metaDTO.setId(metaSalva.getId());

        return metaDTO;
    }

    public List<MetaDTO> listarMetas() {
        List<Meta> metas = metaRepository.findAll();
        return metas.stream().map(meta -> {
            MetaDTO dto = new MetaDTO();
            dto.setId(meta.getId());
            dto.setNome(meta.getNome());
            dto.setDescricao(meta.getDescricao());
            dto.setUsuarioId(meta.getUsuario().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}
