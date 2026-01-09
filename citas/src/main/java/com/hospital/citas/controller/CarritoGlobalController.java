package com.hospital.citas.controller;

import com.hospital.citas.model.Carrito;
import com.hospital.citas.model.Paciente;
import com.hospital.citas.model.Usuario;
import com.hospital.citas.service.CarritoService;
import com.hospital.citas.service.PacienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CarritoGlobalController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private PacienteService pacienteService;

    @ModelAttribute("carrito")
    public Carrito carrito(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return null;

        Paciente paciente = pacienteService.buscarPorUsuario(usuario);
        if (paciente == null) return null;

        return carritoService.obtenerCarritoActivo(paciente);
    }
}

