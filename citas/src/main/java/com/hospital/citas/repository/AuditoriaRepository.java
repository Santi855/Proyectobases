package com.hospital.citas.repository;

import com.hospital.citas.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer> {
    // Usamos 'IdLog' porque así se llama tu variable en la clase Auditoria
    List<Auditoria> findTop5ByOrderByIdLogDesc();
}