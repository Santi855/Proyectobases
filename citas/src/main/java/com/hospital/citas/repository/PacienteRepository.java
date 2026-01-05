package com.hospital.citas.repository;

import com.hospital.citas.model.Paciente;
import com.hospital.citas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;





@Repository

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    @Query("SELECT p FROM Paciente p WHERE p.usuario.id = :idUsuario")
    Paciente findByUsuarioId(@Param("idUsuario") Integer idUsuario);
}
