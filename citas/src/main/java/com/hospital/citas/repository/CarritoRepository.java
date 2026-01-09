package com.hospital.citas.repository;

import com.hospital.citas.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    @Query("""
        SELECT c FROM Carrito c
        WHERE c.paciente.id = :idPaciente
          AND c.pagado = false
    """)
    Carrito carritoActivo(@Param("idPaciente") Integer idPaciente);

    Optional<Carrito> findFirstByOrderByIdCarritoDesc();

}

