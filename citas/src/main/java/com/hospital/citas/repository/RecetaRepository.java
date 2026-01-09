package com.hospital.citas.repository;

import com.hospital.citas.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
<<<<<<< HEAD

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
=======
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {

    // =================================================================
    // 1. MÉTODOS DE BÚSQUEDA Y FILTRADO (NUEVOS)
    // =================================================================

    // Buscar recetas por la Cédula Profesional del Doctor (Usando HQL para la tabla de gestión)
    @Query("SELECT r FROM Receta r WHERE r.cita.doctor.cedulaProfesional LIKE %:cedula%")
    List<Receta> buscarPorCedulaDoctor(@Param("cedula") String cedula);

    // =================================================================
    // 2. MÉTODOS DE ELIMINACIÓN (EXISTENTES)
    // =================================================================

    @Query("SELECT r FROM Receta r WHERE r.idDoctor = :idDoctor")
    List<Receta> findAllByDoctor_IdDoctor(@Param("idDoctor") Integer idDoctor);

    @Query("SELECT r FROM Receta r WHERE r.cita.idCita = :idCita")
    List<Receta> findAllByCita_IdCita(@Param("idCita") Integer idCita);

    // =================================================================
    // 3. PROCEDIMIENTOS ALMACENADOS
    // =================================================================

    @Query(value = "EXEC SP_GenerarReceta :IdCita, :IdDoctor, :IdPaciente, :Diagnostico, :Tratamiento, :Medicamentos", nativeQuery = true)
    Integer generarReceta(
        @Param("IdCita") Integer idCita,
        @Param("IdDoctor") Integer idDoctor,
        @Param("IdPaciente") Integer idPaciente,
        @Param("Diagnostico") String diagnostico,
        @Param("Tratamiento") String tratamiento,
        @Param("Medicamentos") String medicamentos
    );

    // Este es el que ya tenías, ideal para reportes específicos
    @Query(value = "EXEC SP_MostrarRecetasPorMedico :CedulaMedico", nativeQuery = true)
    List<Object[]> buscarRecetasPorMedico(@Param("CedulaMedico") String cedulaMedico);
}
>>>>>>> b3cee62 (Orden final: archivos movidos a la raiz y limpieza de basura)
