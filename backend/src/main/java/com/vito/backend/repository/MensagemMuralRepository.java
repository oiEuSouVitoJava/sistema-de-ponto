// src/main/java/com/vito/backend/repository/MensagemMuralRepository.java
package com.vito.backend.repository;

import com.vito.backend.model.MensagemMural;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemMuralRepository extends JpaRepository<MensagemMural, Long> {
}
