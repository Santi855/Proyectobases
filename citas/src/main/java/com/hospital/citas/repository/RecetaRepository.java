package com.hospital.citas.repository;

import com.hospital.citas.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Integer> {

    @Query(value = """
        SELECT *
        FROM Recetas
        WHERE id_paciente = :idPaciente
        ORDER BY fecha_emision DESC
    """, nativeQuery = true)
    List<Receta> buscarPorPaciente(@Param("idPaciente") Integer idPaciente);

    boolean existsByCita_IdCita(Integer idCita);

    Receta findByCita_IdCita(Integer idCita);
}
