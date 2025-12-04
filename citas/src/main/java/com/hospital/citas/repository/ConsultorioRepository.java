package com.hospital.citas.repository;

import com.hospital.citas.model.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultorioRepository extends JpaRepository<Consultorio, Integer> {

    // 🔹 Busca el consultorio asignado a un doctor específico
    Consultorio findByDoctor_IdDoctor(Integer idDoctor);
}
