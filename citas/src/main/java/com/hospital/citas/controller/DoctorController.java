package com.hospital.citas.controller;

import com.hospital.citas.service.RecetaService;
import com.hospital.citas.service.CitaService;
import com.hospital.citas.model.Cita;
import com.hospital.citas.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired private RecetaService recetaService;
    @Autowired private CitaService citaService;
    @Autowired private CitaRepository citaRepository;

    // ====================================================================
    // 1. FORMULARIO RECETA (Carga datos automáticos de la cita)
    // ====================================================================
    @GetMapping("/formulario-receta/{idCita}")
    public String mostrarFormularioReceta(@PathVariable("idCita") Integer idCita, Model model) {
        
        Cita cita = citaRepository.findById(idCita).orElse(null);
        
        if (cita != null) {
            model.addAttribute("idCita", cita.getIdCita());
            model.addAttribute("idDoctor", cita.getDoctor().getIdDoctor());
            model.addAttribute("idPaciente", cita.getPaciente().getId());
        }
        
        // Apunta a templates/Receta/formulario-receta.html
        return "Receta/formulario-receta"; 
    }

    // ====================================================================
    // 2. PROCESAR RECETA (Llama al servicio con 6 parámetros)
    // ====================================================================
    @PostMapping("/generar-receta")
    public String generarReceta(
        @RequestParam("citaId") Integer citaId,
        @RequestParam("doctorId") Integer doctorId,
        @RequestParam("pacienteId") Integer pacienteId,
        @RequestParam("diagnostico") String diagnostico,
        @RequestParam("tratamiento") String tratamiento,
        @RequestParam("medicamentos") String medicamentos,
        Model model) {

        try {
            // Llamada al servicio que ejecuta el SP
            Integer numReceta = recetaService.guardarNuevaReceta(
                citaId, doctorId, pacienteId, diagnostico, tratamiento, medicamentos
            );

            // Pasamos datos a la vista de éxito
            model.addAttribute("numReceta", numReceta);
            model.addAttribute("idDoctor", doctorId); // Para el botón de regresar
            model.addAttribute("mensajeExito", "Receta N°" + numReceta + " generada con éxito.");
            
            // CORREGIDO: Apunta a templates/Receta/confirmacion-receta.html
            return "Receta/confirmacion-receta"; 

        } catch (Exception e) {
            // Recargamos los IDs para que el formulario no aparezca vacío en caso de error
            model.addAttribute("mensajeError", "Error en la base de datos: " + e.getMessage());
            model.addAttribute("idCita", citaId);
            model.addAttribute("idDoctor", doctorId);
            model.addAttribute("idPaciente", pacienteId);
            
            // Regresa al formulario dentro de la carpeta Receta
            return "Receta/formulario-receta"; 
        }
    }

    // ====================================================================
    // 3. MOSTRAR CITAS DEL MÉDICO (Dashboard)
    // ====================================================================
    @GetMapping("/citas/dashboard")
    public String mostrarDashboardCitas(@RequestParam("doctorId") Integer idDoctor, Model model) {
        try {
            List<Object[]> citas = citaService.obtenerCitasPorDoctor(idDoctor);
            model.addAttribute("citas", citas);
            model.addAttribute("idDoctor", idDoctor);
            return "Doctor/citas-medico-tabla"; 
        } catch (Exception e) {
            model.addAttribute("error", "No se pudieron cargar las citas: " + e.getMessage());
            return "error-page"; 
        }
    }
}