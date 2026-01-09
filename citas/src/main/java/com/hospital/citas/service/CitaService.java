package com.hospital.citas.service;

import com.hospital.citas.model.Cita;
import com.hospital.citas.model.Doctor;
import com.hospital.citas.repository.CitaRepository;
import com.hospital.citas.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;
import java.util.List; // Importar List
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private DoctorRepository doctorRepository;



    @Transactional
    public void guardarCita(Cita cita) {
        validarCita(cita);
        citaRepository.save(cita);
    }
    public List<String> obtenerHorasDisponibles(Integer idDoctor, LocalDate fecha) {

        Doctor doctor = doctorRepository.findById(idDoctor).orElse(null);
        if (doctor == null) {
            return new ArrayList<>();
        }

        LocalTime inicio = doctor.getHorarioInicio();
        LocalTime fin = doctor.getHorarioFin();

        List<String> horas = new ArrayList<>();

        while (!inicio.isAfter(fin.minusHours(1))) {

            Boolean disponible = doctorRepository.doctorDisponible(idDoctor, fecha, inicio);

            if (Boolean.TRUE.equals(disponible)) {
                horas.add(inicio.toString());
            }

            inicio = inicio.plusHours(1);
        }

        return horas;
    }

    public List<Cita> obtenerCitasPorDoctor(Integer idDoctor) {
        return citaRepository.findByDoctor(idDoctor);
    }
    public List<Cita> obtenerHistorialPaciente(Integer idPaciente) {
        return citaRepository.obtenerHistorialPaciente(idPaciente);
    }




    private void validarCita(Cita cita) {

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaHoraCita = LocalDateTime.of(cita.getFechaCita(), cita.getHoraCita());

        if (fechaHoraCita.isBefore(ahora)) {
            throw new IllegalArgumentException("❌ No puedes agendar una cita con fecha u hora pasada.");
        }

        Duration diferencia = Duration.between(ahora, fechaHoraCita);
        if (diferencia.toHours() < 48) {
            throw new IllegalArgumentException("⚠️ La cita debe agendarse con al menos 48 horas de anticipación.");
        }

        if (cita.getFechaCita().isAfter(LocalDate.now().plusMonths(3))) {
            throw new IllegalArgumentException("⚠️ No se pueden agendar citas con más de 3 meses de anticipación.");
        }



        Boolean disponible = citaRepository.doctorDisponible(
                cita.getDoctor().getIdDoctor(),
                cita.getFechaCita(),
                cita.getHoraCita()
        );

        if (disponible == null || !disponible) {
            throw new RuntimeException("El doctor no está disponible en esa fecha y hora.");
        }


        int citasPendientes = citaRepository.countCitasPendientesPacienteDoctor(
                cita.getPaciente().getId(),
                cita.getDoctor().getIdDoctor()
        );
        if (citasPendientes > 0) {
            throw new IllegalArgumentException("⚠️ Ya tienes una cita pendiente con este doctor.");
        }

        cita.setEstatus("Agendada pendiente de pago");

        cita.setFechaRegistro(LocalDateTime.now());
    }

<<<<<<< HEAD
    public Cita buscarPorId(Integer id) {
        return citaRepository.findById(id).orElse(null);
    }

    public void marcarNoAsistio(Integer idCita) {
        Cita cita = buscarPorId(idCita);
        if (cita != null) {
            cita.setEstatus("NO_ASISTIO");
            citaRepository.save(cita);
        }
    }

    public void marcarAtendida(Integer idCita) {
        Cita cita = buscarPorId(idCita);
        if (cita != null) {
            cita.setEstatus("ATENDIDA");
            citaRepository.save(cita);
        }
=======

    @Transactional(readOnly = true)
    public List<Object[]> obtenerCitasPorDoctor(Integer idDoctor) {
        // El repositorio debe tener el método buscarCitasPorDoctor(Integer)
        return citaRepository.buscarCitasPorDoctor(idDoctor);
>>>>>>> b3cee62 (Orden final: archivos movidos a la raiz y limpieza de basura)
    }
}
