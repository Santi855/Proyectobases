package com.hospital.citas.repository;

import com.hospital.citas.model.VwCitasPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VwCitasPacienteRepository extends JpaRepository<VwCitasPaciente, Integer> {
    List<VwCitasPaciente> findByIdPaciente(Integer idPaciente);
}
