package com.hospital.citas.controller;

import com.hospital.citas.model.Cita;
import com.hospital.citas.model.Paciente;
import com.hospital.citas.service.CitaService;
import com.hospital.citas.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorHistorialController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/historial/{idPaciente}")
    public String verHistorialPaciente(
            @PathVariable Integer idPaciente,
            Model model) {

        Paciente paciente = pacienteService.buscarPorId(idPaciente);
        List<Cita> historial = citaService.obtenerHistorialPaciente(idPaciente);

        model.addAttribute("paciente", paciente);
        model.addAttribute("historial", historial);
        model.addAttribute("citas",
                citaService.obtenerHistorialPaciente(idPaciente));

        return "Doctor/historial";
    }
}
