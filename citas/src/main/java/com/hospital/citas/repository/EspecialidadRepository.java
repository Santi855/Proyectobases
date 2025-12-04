package com.hospital.citas.repository;


import com.hospital.citas.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {

    // 🔹 Este método ya viene por defecto:
    // List<Especialidad> findAll();

}