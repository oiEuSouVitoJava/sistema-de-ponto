package com.vito.backend.controller;

import com.vito.backend.DTO.MarcacaoDTO;
import com.vito.backend.service.MarcacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcacoes")
public class MarcacaoController {

    @Autowired
    private MarcacaoService marcacaoService;

    @PostMapping
    public MarcacaoDTO criarMarcacao(@RequestBody MarcacaoDTO dto) {
        return marcacaoService.criarMarcacao(dto);
    }

    @GetMapping
    public List<MarcacaoDTO> listarMarcacoes() {
        return marcacaoService.listarTodas();
    }
}
