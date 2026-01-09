package com.hospital.citas.repository;

import com.hospital.citas.model.Cita; // Asume que tienes un modelo Cita.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepcionistaRepository extends JpaRepository<Cita, Integer> { 

    /**
     * Ejecuta el SP_CalcularTotalPagar y devuelve el monto.
     */
    @Procedure(procedureName = "SP_CalcularTotalPagar")
    Double calcularTotalPagar(
        @Param("IdCita") Integer idCita
    );
}