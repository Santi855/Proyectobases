package com.hospital.citas.controller;

import com.hospital.citas.model.Paciente;
import com.hospital.citas.model.Usuario;
import com.hospital.citas.service.PacienteService;
import com.hospital.citas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "paciente/registro";
    }



    @PostMapping("/registrar")
    public String registrarPaciente(
            @RequestParam String nombre,
            @RequestParam String apellido_paterno,
            @RequestParam String apellido_materno,
            @RequestParam String usuario,
            @RequestParam String contrasena,
            @RequestParam String correo,
            @RequestParam String telefono,
            @RequestParam String fechaNacimiento,
            @RequestParam(required = false) String curp,
            @RequestParam(required = false) String tipoSangre,
            @RequestParam(required = false) String alergias,
            @RequestParam(required = false) String padecimientosPrevios,
            @RequestParam(required = false) Double peso,
            @RequestParam(required = false) Double estatura,
            Model model) {

        if (usuarioService.buscarPorUsuario(usuario) != null) {
            model.addAttribute("error", "El usuario ya existe.");
            return "paciente/registro";
        }

        Usuario nuevoUsuario = usuarioService.registrarUsuario(
                nombre, apellido_paterno, apellido_materno, correo, telefono, usuario, contrasena, "Paciente");

        Paciente paciente = new Paciente();
        paciente.setUsuario(nuevoUsuario);
        paciente.setCurp(curp);
        paciente.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
        paciente.setTipoSangre(tipoSangre);
        paciente.setAlergias(alergias);
        paciente.setPadecimientosPrevios(padecimientosPrevios);
        paciente.setPeso(peso);
        paciente.setEstatura(estatura);
        pacienteService.guardarPaciente(paciente);

        model.addAttribute("mensaje", "Registro exitoso. Ahora puedes iniciar sesión.");
        return "login";
    }
}
