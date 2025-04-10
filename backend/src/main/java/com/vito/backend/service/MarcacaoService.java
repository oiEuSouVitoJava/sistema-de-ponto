package com.vito.backend.service;

import com.vito.backend.DTO.MarcacaoDTO;
import com.vito.backend.model.Marcacao;
import com.vito.backend.model.Meta;
import com.vito.backend.model.Usuario;
import com.vito.backend.repository.MarcacaoRepository;
import com.vito.backend.repository.MetaRepository;
import com.vito.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MarcacaoService {

    @Autowired
    private MarcacaoRepository marcacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MetaRepository metaRepository;

    public MarcacaoDTO criarMarcacao(MarcacaoDTO dto) {
        Marcacao marcacao = new Marcacao();
        marcacao.setDataHora(dto.getDataHora());
        marcacao.setObservacao(dto.getObservacao());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Meta meta = metaRepository.findById(dto.getMetaId())
                .orElseThrow(() -> new RuntimeException("Meta não encontrada"));

        marcacao.setUsuario(usuario);
        marcacao.setMeta(meta);

        Marcacao salva = marcacaoRepository.save(marcacao);
        dto.setId(salva.getId());
        return dto;
    }

    public List<MarcacaoDTO> listarTodas() {
        return marcacaoRepository.findAll().stream().map(m -> {
            MarcacaoDTO dto = new MarcacaoDTO();
            dto.setId(m.getId());
            dto.setDataHora(m.getDataHora());
            dto.setObservacao(m.getObservacao());
            dto.setUsuarioId(m.getUsuario().getId());
            dto.setMetaId(m.getMeta().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<MarcacaoDTO> listar() {
        return marcacaoRepository.findAll().stream().map(m -> {
            MarcacaoDTO dto = new MarcacaoDTO();
            dto.setId(m.getId());
            dto.setDataHora(m.getDataHora());
            dto.setObservacao(m.getObservacao());

            if (m.getUsuario() != null) {
                dto.setUsuarioId(m.getUsuario().getId());
                dto.setUsuarioNome(m.getUsuario().getNome());
            }

            if (m.getMeta() != null) {
                dto.setMetaId(m.getMeta().getId());
                dto.setMetaNome(m.getMeta().getNome());
                dto.setMetaDescricao(m.getMeta().getDescricao());
            }

            return dto;
        }).toList();
    }



}
