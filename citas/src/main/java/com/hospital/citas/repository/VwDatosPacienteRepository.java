package com.hospital.citas.repository;

import com.hospital.citas.model.VwDatosPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VwDatosPacienteRepository extends JpaRepository<VwDatosPaciente, Integer> {

    @Query("SELECT v FROM VwDatosPaciente v WHERE v.idPaciente = :idPaciente")
    VwDatosPaciente findByIdPaciente(@Param("idPaciente") Integer idPaciente);
}