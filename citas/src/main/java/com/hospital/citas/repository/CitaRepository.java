package com.hospital.citas.repository;

import com.hospital.citas.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
