package com.hospital.citas.controller;

import com.hospital.citas.model.Usuario;
import com.hospital.citas.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String contrasena,
                        Model model,
                        HttpSession session) {

        Usuario user = usuarioService.buscarPorUsuario(usuario);

        if (user == null || !usuarioService.validarCredenciales(usuario, contrasena)) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
            return "login";
        }

        session.setAttribute("usuarioLogueado", user);

        String rol = user.getRol();

        if ("Recepcionista".equalsIgnoreCase(rol)) {
            return "redirect:/Recepcionista/home";
        } else if ("Doctor".equalsIgnoreCase(rol)) {
            return "redirect:/doctor/home";
        } else if ("Paciente".equalsIgnoreCase(rol)) {
            return "redirect:/Paciente/home";
        } else {
            return "redirect:/admin/home";
        }
    }

}