package com.hospital.citas.controller;

import com.hospital.citas.model.Doctor;
import com.hospital.citas.model.Usuario;
import com.hospital.citas.model.Especialidad;
import com.hospital.citas.service.DoctorService;
import com.hospital.citas.service.UsuarioService;
import com.hospital.citas.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/doctores")
public class DoctorAdminController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping("")
    public String listarDoctores(Model model) {

        List<Doctor> doctores = doctorService.findAll();
        model.addAttribute("doctores", doctores);
        return "Admin/doctores/crdoctores";
    }

    @GetMapping("/nuevo")
    public String nuevoDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("especialidades", especialidadService.findAll());
        return "admin/doctores/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarDoctor(@ModelAttribute Doctor doctor) {
        doctorService.guardar(doctor);
        return "redirect:/admin/doctores";
    }

    @GetMapping("/editar/{id}")
    public String editarDoctor(@PathVariable Integer id, Model model) {

        Doctor doctor = doctorService.buscarPorId(id);
        List<Usuario> usuarios = usuarioService.findAll();
        List<Especialidad> especialidades = especialidadService.findAll();

        model.addAttribute("doctor", doctor);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("especialidades", especialidades);

        return "Admin/doctores/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarDoctor(@ModelAttribute Doctor doctor) {
        doctorService.guardar(doctor);
        return "redirect:/admin/doctores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDoctor(@PathVariable Integer id) {
        doctorService.eliminar(id);
        return "redirect:/admin/doctores";
    }
}
