package com.hospital.citas.controller;

import com.hospital.citas.model.Carrito;;
import com.hospital.citas.model.Paciente;;
import com.hospital.citas.model.Usuario;;
import com.hospital.citas.service.CarritoService;;
import com.hospital.citas.service.PacienteService;;
import jakarta.servlet.http.HttpSession;;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Controller;;
import org.springframework.ui.Model;;
import org.springframework.web.bind.annotation.GetMapping;;
import org.springframework.web.bind.annotation.RequestMapping;;
@Controller
@RequestMapping("/Paciente/carrito")
public class CarritoViewController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public String verCarrito(HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        Paciente paciente = pacienteService.buscarPorUsuario(usuario);

        Carrito carrito = carritoService.obtenerCarritoActivo(paciente);

        model.addAttribute("carrito", carrito);
        return "Paciente/carrito";
    }
}

