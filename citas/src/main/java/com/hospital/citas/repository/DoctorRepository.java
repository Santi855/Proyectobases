package com.hospital.citas.repository;

import com.hospital.citas.model.Doctor;
import com.hospital.citas.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

<<<<<<< HEAD


=======
>>>>>>> b3cee62 (Orden final: archivos movidos a la raiz y limpieza de basura)
    @Query("SELECT d FROM Doctor d WHERE d.usuario.nombre LIKE %:nombre% OR d.usuario.apellido_paterno LIKE %:nombre%")
    List<Doctor> findByNombre(@Param("nombre") String nombre);

    @Query(value = "SELECT dbo.fn_doctorDisponible(:idDoctor, :fecha, :hora)", nativeQuery = true)
    Boolean doctorDisponible(@Param("idDoctor") Integer idDoctor,
                             @Param("fecha") LocalDate fecha,
                             @Param("hora") LocalTime hora);

    @Query("SELECT d FROM Doctor d WHERE d.especialidad.id = :idEspecialidad")
    List<Doctor> findByEspecialidad(Integer idEspecialidad);

    List<Doctor> findByEspecialidad_Id(Integer idEspecialidad);

    @Query("SELECT COUNT(c) FROM Cita c WHERE c.doctor.idDoctor = :idDoctor " +
            "AND c.estatus IN ('Agendada pendiente de pago', 'Pagada pendiente por atender')")
    int contarCitasPendientes(Integer idDoctor);
<<<<<<< HEAD


=======
>>>>>>> b3cee62 (Orden final: archivos movidos a la raiz y limpieza de basura)

    @Query("SELECT d FROM Doctor d WHERE d.usuario.id = :idUsuario")
    Doctor findByUsuarioId(@Param("idUsuario") Integer idUsuario);

    // ESTA ES LA LÍNEA QUE NECESITAMOS PARA LA GRÁFICA
    long countByEspecialidad(Especialidad e);
}