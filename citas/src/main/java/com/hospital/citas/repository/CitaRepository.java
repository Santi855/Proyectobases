package com.hospital.citas.repository;

import com.hospital.citas.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.data.repository.query.Param;
import java.util.List; // Importar List
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {



    @Query(value = "SELECT dbo.fn_doctorDisponible(:idDoctor, :fecha, :hora)", nativeQuery = true)
    Boolean doctorDisponible(
            @Param("idDoctor") Integer idDoctor,
            @Param("fecha") LocalDate fecha,
            @Param("hora") LocalTime hora
    );


    @Query(value = """
        SELECT COUNT(*) 
        FROM Citas 
        WHERE id_paciente = :idPaciente
          AND id_doctor = :idDoctor
          AND estatus IN ('Agendada pendiente de pago', 'Pagada pendiente por atender')
        """, nativeQuery = true)
    int countCitasPendientesPacienteDoctor(@Param("idPaciente") int idPaciente,
                                           @Param("idDoctor") int idDoctor);

    @Query(value = "SELECT hora_cita FROM fn_HorariosOcupados(:idDoctor, :fecha)", nativeQuery = true)
    List<String> horasOcupadas(
            @Param("idDoctor") Integer idDoctor,
            @Param("fecha") String fecha
    );
<<<<<<< HEAD
    @Query("""
        SELECT c
        FROM Cita c
        WHERE c.doctor.usuario.id = :idDoctor
        ORDER BY c.fechaCita, c.horaCita
    """)
    List<Cita> findByDoctor(@Param("idDoctor") Integer idDoctor);



    @Query("""
        SELECT c
        FROM Cita c
        WHERE c.paciente.id = :idPaciente
        ORDER BY c.fechaCita DESC
    """)
    List<Cita> obtenerHistorialPaciente(@Param("idPaciente") Integer idPaciente);




=======
    @Procedure(procedureName = "SP_MostrarCitasPorDoctor")
    List<Object[]> buscarCitasPorDoctor(
        @Param("IdDoctor") Integer idDoctor
    );


    List<Cita> findAllByPaciente_Id(Integer pacienteId);

   
// Cambiamos SUM(c.costo) por COUNT(c) para evitar el error
@Query("SELECT c.especialidad.nombre, COUNT(c) FROM Cita c GROUP BY c.especialidad.nombre")
    List<Object[]> countCitasByEspecialidad();

    // Consulta 2: Ranking de doctores por cantidad de citas (Para evitar el error de 'costo')
    @Query("SELECT c.doctor.usuario.nombre, COUNT(c) FROM Cita c GROUP BY c.doctor.usuario.nombre")
    List<Object[]> findRankingDoctores();
>>>>>>> b3cee62 (Orden final: archivos movidos a la raiz y limpieza de basura)
}
