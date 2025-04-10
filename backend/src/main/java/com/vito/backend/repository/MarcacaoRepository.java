package com.vito.backend.repository;

import com.vito.backend.model.Marcacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcacaoRepository extends JpaRepository<Marcacao, Long> {
}
