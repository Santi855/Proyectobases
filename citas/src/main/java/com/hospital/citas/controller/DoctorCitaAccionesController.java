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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DoctorCitaAccionesController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/doctor/home/no-asistio/{id}")
    public String marcarNoAsistio(@PathVariable Integer id, HttpSession session) {

        Usuario doctor = usuarioService.obtenerUsuarioActual(session);
        if (doctor == null || !doctor.getRol().equals("Doctor")) {
            return "redirect:/login";
        }

        citaService.marcarNoAsistio(id);
        return "redirect:/doctor/home";
    }

    @GetMapping("/doctor/home/atendida/{id}")
    public String marcarAtendida(@PathVariable Integer id, HttpSession session) {

        Usuario doctor = usuarioService.obtenerUsuarioActual(session);
        if (doctor == null || !doctor.getRol().equals("Doctor")) {
            return "redirect:/login";
        }

        citaService.marcarAtendida(id);
        return "redirect:/doctor/home";
    }

    @GetMapping("/doctor/home/receta/{id}")
    public String agregarReceta(@PathVariable Integer id, Model model, HttpSession session) {

        Usuario doctor = usuarioService.obtenerUsuarioActual(session);
        if (doctor == null || !doctor.getRol().equals("Doctor")) {
            return "redirect:/login";
        }

        Cita cita = citaService.buscarPorId(id);
        model.addAttribute("cita", cita);

        return "Doctor/receta";
    }

}
