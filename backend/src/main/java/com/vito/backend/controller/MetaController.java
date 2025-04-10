package com.vito.backend.controller;

import com.vito.backend.DTO.MetaDTO;
import com.vito.backend.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metas")
@CrossOrigin(origins = "*")
public class MetaController {

    @Autowired
    private MetaService metaService;

    @PostMapping
    public MetaDTO criarMeta(@RequestBody MetaDTO metaDTO) {
        return metaService.criarMeta(metaDTO);
    }

    @GetMapping
    public List<MetaDTO> listarMetas() {
        return metaService.listarMetas();
    }
}
