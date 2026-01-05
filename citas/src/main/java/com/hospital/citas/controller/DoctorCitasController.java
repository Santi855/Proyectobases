package com.hospital.citas.controller;

import com.hospital.citas.model.Cita;
import com.hospital.citas.model.Usuario;
import com.hospital.citas.service.CitaService;
import com.hospital.citas.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DoctorCitasController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/doctor/citas")
    public String verCitasDoctor(HttpSession session, Model model) {

        Usuario doctor = usuarioService.obtenerUsuarioActual(session);

        if (doctor == null || !doctor.getRol().equals("Doctor")) {
            return "redirect:/login";
        }

        List<Cita> citas = citaService.obtenerCitasPorDoctor(doctor.getId());

        model.addAttribute("citas", citas);

        return "Doctor/home";
    }
}
