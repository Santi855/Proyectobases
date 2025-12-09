package com.hospital.citas.controller;

import com.hospital.citas.model.Usuario;
import com.hospital.citas.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorHomeController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/home")
    public String homeDoctor(HttpSession session, Model model) {

        Usuario usuario = usuarioService.obtenerUsuarioActual(session);

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);

        return "Doctor/home";
    }
}
