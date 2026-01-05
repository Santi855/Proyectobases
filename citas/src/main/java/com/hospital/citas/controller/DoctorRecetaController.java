package com.hospital.citas.controller;

import com.hospital.citas.model.Cita;
import com.hospital.citas.model.Receta;
import com.hospital.citas.service.CitaService;
import com.hospital.citas.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctor/receta")
public class DoctorRecetaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private RecetaService recetaService;

    @GetMapping("/agregar/{idCita}")
    public String formularioReceta(@PathVariable Integer idCita, Model model) {

        Cita cita = citaService.buscarPorId(idCita);

        Receta receta = new Receta();
        receta.setCita(cita);
        receta.setDoctor(cita.getDoctor());
        receta.setPaciente(cita.getPaciente());

        model.addAttribute("receta", receta);
        model.addAttribute("cita", cita);

        return "Doctor/receta-form";
    }

    @PostMapping("/guardar")
    public String guardarReceta(@ModelAttribute Receta receta) {

        recetaService.guardar(receta);


        return "redirect:/doctor/home";
    }
    @GetMapping("/ver/{idCita}")
    public String verReceta(@PathVariable Integer idCita, Model model) {

        Receta receta = recetaService.obtenerPorCita(idCita);

        model.addAttribute("receta", receta);

        return "Doctor/receta-ver";
    }
}
