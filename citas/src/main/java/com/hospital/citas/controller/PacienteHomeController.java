package com.hospital.citas.controller;

import com.hospital.citas.model.*;
import com.hospital.citas.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping("/Paciente")
public class PacienteHomeController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private EspecialidadService especialidadService;
    @Autowired private VwDatosPacienteService datosPacienteService;
    @Autowired private VwCitasPacienteService citasPacienteService;
    @Autowired private DoctorService doctorService;
    @Autowired private CitaService citaService;
    @Autowired private ConsultorioService consultorioService;
    @Autowired private PacienteService pacienteService;

    @GetMapping("/horarios/{idDoctor}/{fecha}")
    public ResponseEntity<List<String>> obtenerHorariosDisponibles(
            @PathVariable Integer idDoctor,
            @PathVariable String fecha) {

        LocalDate fechaCita = LocalDate.parse(fecha);
        List<String> horas = citaService.obtenerHorasDisponibles(idDoctor, fechaCita);

        return ResponseEntity.ok(horas);
    }

    @GetMapping("/home")
    public String mostrarHome(Model model, HttpSession session) {

        Usuario usuarioActual = usuarioService.obtenerUsuarioActual(session);

        if (usuarioActual == null) {
            model.addAttribute("error", "Debes iniciar sesión primero.");
            return "login";
        }

        Paciente paciente = pacienteService.buscarPorUsuario(usuarioActual);

        if (paciente == null) {
            model.addAttribute("error", "No se encontró información del paciente.");
            return "Paciente/home";
        }

        model.addAttribute("datos", datosPacienteService.obtenerDatosPorIdPaciente(paciente.getId()));
        model.addAttribute("citas", citasPacienteService.obtenerCitasPorPaciente(paciente.getId()));

        model.addAttribute("citas",
                citasPacienteService.obtenerCitasPorPaciente(paciente.getId()));
        model.addAttribute("especialidades", especialidadService.findAll());
        model.addAttribute("doctores", new ArrayList<>());

        return "Paciente/home";
    }


    @PostMapping("/agendar")
    public String agendarCita(
            @RequestParam("especialidad") Integer idEspecialidad,
            @RequestParam("doctor") Integer idDoctor,
            @RequestParam("fecha") String fecha,
            @RequestParam("hora") String hora,
            HttpSession session,
            Model model) {

        Usuario usuarioActual = usuarioService.obtenerUsuarioActual(session);

        if (usuarioActual == null) {
            model.addAttribute("error", "Debes iniciar sesión primero.");
            return "login";
        }

        Paciente paciente = pacienteService.buscarPorUsuario(usuarioActual);

        try {
            Especialidad especialidad = especialidadService.buscarPorId(idEspecialidad);
            Doctor doctor = doctorService.buscarPorId(idDoctor);
            Consultorio consultorio = consultorioService.obtenerConsultorioPorDoctor(idDoctor);

            Cita cita = new Cita();
            cita.setPaciente(paciente);
            cita.setDoctor(doctor);
            cita.setEspecialidad(especialidad);
            cita.setConsultorio(consultorio);
            cita.setFechaCita(LocalDate.parse(fecha));
            cita.setHoraCita(LocalTime.parse(hora));
            cita.setEstatus("Agendada pendiente de pago");

            citaService.guardarCita(cita);

            model.addAttribute("mensaje", "✅ Cita agendada correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "❌ Error al agendar la cita: " + e.getMessage());
        }

        model.addAttribute("especialidades", especialidadService.findAll());
        model.addAttribute("doctores", new ArrayList<>());
        model.addAttribute("citas", citasPacienteService.obtenerCitasPorPaciente(paciente.getId()));
        model.addAttribute("datos", datosPacienteService.obtenerDatosPorIdPaciente(paciente.getId()));

        return "Paciente/home";
    }

    @GetMapping("/doctores/{idEspecialidad}")
    public ResponseEntity<List<Map<String, Object>>> obtenerDoctoresPorEspecialidad(
            @PathVariable Integer idEspecialidad) {

        List<Doctor> doctores = doctorService.obtenerDoctoresPorEspecialidad(idEspecialidad);

        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Doctor d : doctores) {
            Map<String, Object> map = new HashMap<>();
            map.put("idDoctor", d.getIdDoctor());
            map.put("nombre", d.getUsuario().getNombre());
            map.put("apellido", d.getUsuario().getApellido_paterno());
            respuesta.add(map);
        }

        return ResponseEntity.ok(respuesta);
    }
}
