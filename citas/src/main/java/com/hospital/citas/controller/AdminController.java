package com.hospital.citas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.hospital.citas.repository.*;
import com.hospital.citas.service.CitaPdfService;
import com.hospital.citas.service.UsuarioService;
import com.hospital.citas.util.DoctorExportPDF;
import com.hospital.citas.model.*;

import jakarta.servlet.http.HttpServletResponse;

//
import com.hospital.citas.util.DoctorExportPDF;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
//


// Para que funcione el reverse de la lista
import java.util.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;



@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
private AuditoriaRepository auditoriaRepository;
    @Autowired
private RecetaRepository recetaRepository; // Esto le dice a Spring que use el repositorio que editamos

    @Autowired private PacienteRepository pacienteRepository;
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private EspecialidadRepository especialidadRepository;
    @Autowired private ConsultorioRepository consultorioRepository;
    @Autowired private CitaRepository citaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private UsuarioService usuarioService; 
    @Autowired private CitaPdfService pdfService;

    // ==========================================
    // 1. HOME
    // ==========================================
    @GetMapping("/home")
public String home(Model model) {
    // 1. Datos de las tarjetas
    model.addAttribute("totalUsuarios", usuarioRepository.count());
    model.addAttribute("totalDoctores", doctorRepository.count());
    model.addAttribute("totalCitas", 12); 
    
    // 2. Datos de la bitácora (Timeline)
    model.addAttribute("recientes", auditoriaRepository.findTop5ByOrderByIdLogDesc());
    
    // 3. Datos de la gráfica (Especialidades)
    List<Especialidad> especialidades = especialidadRepository.findAll();
    model.addAttribute("labelsGrafica", especialidades.stream().map(Especialidad::getNombre).toList());
    model.addAttribute("datosGrafica", especialidades.stream().map(e -> doctorRepository.countByEspecialidad(e)).toList());

    // Dentro de public String home(Model model) { ... }


    return "admin/home";
}

    // ==========================================
    // 2. GESTIÓN DE USUARIOS
    // ==========================================
    @GetMapping("/usuarios")
    public String mostrarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "admin/Usuarios/gestionusuarios"; 
    }
<<<<<<< HEAD
    @GetMapping("Doctores/crdoctores")
    public String crdoctores(){
        return "admin/Doctores/crdoctores";
    }

=======

    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/Usuarios/nuevo-usuario";
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido_paterno") String apellido_paterno,
            @RequestParam("apellido_materno") String apellido_materno,
            @RequestParam("correo") String correo,
            @RequestParam("telefono") String telefono,
            @RequestParam("usuario") String username,
            @RequestParam("contrasena") String contrasena,
            @RequestParam("rol") String rol,
            RedirectAttributes flash) {
        try {
            usuarioService.registrarUsuario(nombre, apellido_paterno, apellido_materno, 
                                          correo, telefono, username, contrasena, rol);
            flash.addFlashAttribute("mensajeExito", "✅ Usuario registrado con éxito.");
        } catch (Exception e) {
            flash.addFlashAttribute("mensajeExito", "❌ Error al registrar: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            return "admin/Usuarios/editar-usuario";
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/actualizar")
    public String actualizarUsuario(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes flash) {
        try {
            Usuario usuarioExistente = usuarioService.obtenerPorId(usuario.getId()).orElse(null);
            if (usuarioExistente != null) {
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setApellido_paterno(usuario.getApellido_paterno());
                usuarioExistente.setApellido_materno(usuario.getApellido_materno());
                usuarioExistente.setCorreo(usuario.getCorreo());
                usuarioExistente.setTelefono(usuario.getTelefono());
                usuarioExistente.setRol(usuario.getRol());
                usuarioService.guardarUsuario(usuarioExistente);
                flash.addFlashAttribute("mensajeExito", "✅ Usuario actualizado correctamente.");
            }
        } catch (Exception e) {
            flash.addFlashAttribute("mensajeExito", "❌ Error al actualizar: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

   @GetMapping("/usuarios/desactivar/{id}")
public String desactivarUsuario(@PathVariable("id") Integer id, RedirectAttributes flash) {
    try {
        Usuario u = usuarioRepository.findById(id).orElse(null);
        if (u != null) {
            u.setActivo(false);
            usuarioRepository.save(u);
            
            // GUARDAR EN AUDITORÍA
            auditoriaRepository.save(new Auditoria(
                "ADMIN_ACTUAL", // Aquí luego pondremos el usuario logueado con Security
                "DESACTIVAR", 
                "Se desactivó el acceso al usuario: " + u.getNombre() + " (ID: " + id + ")"
            ));

            flash.addFlashAttribute("mensajeExito", "🔒 Usuario desactivado y registrado en bitácora.");
        }
    } catch (Exception e) {
        flash.addFlashAttribute("mensajeError", "Error al procesar.");
    }
    return "redirect:/admin/usuarios";
}

@GetMapping("/usuarios/activar/{id}")
public String activarUsuario(@PathVariable("id") Integer id, RedirectAttributes flash) {
    Usuario u = usuarioRepository.findById(id).orElse(null);
    if (u != null) {
        u.setActivo(true);
        usuarioRepository.save(u);
        
        // REGISTRO EN BITÁCORA
        auditoriaRepository.save(new Auditoria(
            "Administrador", 
            "ACTIVAR", 
            "Se restauró el acceso al usuario: " + u.getNombre() + " (ID: " + id + ")"
        ));
        
        flash.addFlashAttribute("mensajeExito", "✅ Usuario reactivado y registrado en monitor.");
    }
    return "redirect:/admin/usuarios";
}

    // ==========================================
    // 3. GESTIÓN DE DOCTORES (NUEVA SECCIÓN)
    // ==========================================
    @GetMapping("/doctores")
    public String listarDoctores(Model model) {
        model.addAttribute("doctores", doctorRepository.findAll());
        return "admin/Doctores/gestiondoctores";
    }

    @GetMapping("/doctores/eliminar/{id}")
    public String eliminarDoctor(@PathVariable("id") Integer id, RedirectAttributes flash) {
        try {
            Doctor doc = doctorRepository.findById(id).orElse(null);
            if (doc != null) {
                // Usamos la eliminación segura del service que ya limpia recetas
                usuarioService.eliminarUsuario(doc.getUsuario().getId());
                flash.addFlashAttribute("mensajeExito", "✅ Doctor eliminado correctamente.");
            }
        } catch (Exception e) {
            flash.addFlashAttribute("mensajeExito", "❌ Error al eliminar doctor: " + e.getMessage());
        }
        return "redirect:/admin/doctores";
    }

    // Dentro de AdminController.java
@GetMapping("/doctores/nuevo")
public String nuevoDoctor(Model model) {
    // IMPORTANTE: Asegúrate de que el nombre sea "doctor" en minúsculas
    model.addAttribute("doctor", new Doctor());
    
    // Cargamos las listas
    model.addAttribute("usuarios", usuarioRepository.findAll());
    model.addAttribute("especialidades", especialidadRepository.findAll());
    
    return "admin/Doctores/crdoctores"; 
}

@GetMapping("/doctores/editar/{id}")
    public String editarDoctor(@PathVariable("id") Integer id, Model model) {
        // Buscamos el doctor por su ID
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        
        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            // Necesitamos pasar estas listas para que los selectores del formulario funcionen
            model.addAttribute("usuarios", usuarioService.findAll());
            model.addAttribute("especialidades", especialidadRepository.findAll());
            return "admin/Doctores/editar";
        }
        return "redirect:/admin/doctores";
    }

    // --- PROCESAR ACTUALIZACIÓN DE DOCTOR ---
    @PostMapping("/doctores/actualizar")
    public String actualizarDoctor(@RequestParam("id_d") Integer idD,
                                   @RequestParam("id_u") Integer idU,
                                   @RequestParam("id_e") Integer idE,
                                   @RequestParam("cedula") String cedula,
                                   @RequestParam("h_inicio") String hInicio,
                                   @RequestParam("h_fin") String hFin,
                                   RedirectAttributes flash) {
        try {
            Doctor d = doctorRepository.findById(idD).orElse(null);
            if (d != null) {
                // Actualizamos la vinculación con Usuario y Especialidad
                d.setUsuario(usuarioService.obtenerPorId(idU).orElse(null));
                d.setEspecialidad(especialidadRepository.findById(idE).orElse(null));
                
                // Actualizamos datos profesionales
                d.setCedulaProfesional(cedula);
                d.setHorarioInicio(LocalTime.parse(hInicio));
                d.setHorarioFin(LocalTime.parse(hFin));
                
                doctorRepository.save(d);
                flash.addFlashAttribute("mensajeExito", "✅ Información del doctor actualizada.");
            }
        } catch (Exception e) {
            flash.addFlashAttribute("mensajeExito", "❌ Error al actualizar: " + e.getMessage());
        }
        return "redirect:/admin/doctores";
    }

    @PostMapping("/doctores/guardar")
public String guardarDoctor(@RequestParam("id_u") Integer idU,
                            @RequestParam("id_e") Integer idE,
                            @RequestParam("cedula") String cedula,
                            @RequestParam("h_inicio") String hInicio,
                            @RequestParam("h_fin") String hFin,
                            RedirectAttributes flash) {
    try {
        Doctor d = new Doctor();
        d.setUsuario(usuarioService.obtenerPorId(idU).orElse(null));
        d.setEspecialidad(especialidadRepository.findById(idE).orElse(null));
        d.setCedulaProfesional(cedula);
        d.setHorarioInicio(LocalTime.parse(hInicio));
        d.setHorarioFin(LocalTime.parse(hFin));
        
        doctorRepository.save(d);
        flash.addFlashAttribute("mensajeExito", "✅ Doctor registrado correctamente.");
    } catch (Exception e) {
        flash.addFlashAttribute("mensajeExito", "❌ Error: " + e.getMessage());
    }
    return "redirect:/admin/doctores";
}

// MÉTODO DESACTIVAR DOCTOR
@GetMapping("/doctores/desactivar/{id}")
public String desactivarDoctor(@PathVariable("id") Integer id, RedirectAttributes flash) {
    Doctor doc = doctorRepository.findById(id).orElse(null);
    if (doc != null) {
        // Obtenemos el usuario vinculado al doctor para desactivar su acceso también
        Usuario u = doc.getUsuario();
        if (u != null) {
            u.setActivo(false);
            usuarioRepository.save(u);
        }
        
        auditoriaRepository.save(new Auditoria(
            "Administrador", 
            "DESACTIVAR_DOCTOR", 
            "Se desactivó al Dr. " + doc.getNombreCompleto() + " (ID: " + id + ")"
        ));
        
        flash.addFlashAttribute("mensajeExito", "🔒 Doctor y su acceso de usuario han sido desactivados.");
    }
    return "redirect:/admin/doctores";
}

// MÉTODO ACTIVAR DOCTOR
@GetMapping("/doctores/activar/{id}")
public String activarDoctor(@PathVariable("id") Integer id, RedirectAttributes flash) {
    Doctor doc = doctorRepository.findById(id).orElse(null);
    if (doc != null) {
        Usuario u = doc.getUsuario();
        if (u != null) {
            u.setActivo(true);
            usuarioRepository.save(u);
        }
        
        auditoriaRepository.save(new Auditoria(
            "Administrador", 
            "ACTIVAR_DOCTOR", 
            "Se restauró el acceso al Dr. " + doc.getNombreCompleto() + " (ID: " + id + ")"
        ));
        
        flash.addFlashAttribute("mensajeExito", "✅ El Doctor ahora está activo nuevamente.");
    }
    return "redirect:/admin/doctores";
}

@GetMapping("/doctores/exportarPDF")
public void exportarDoctoresPDF(HttpServletResponse response) throws IOException {
    response.setContentType("application/pdf");
    String cabecera = "Content-Disposition";
    String valor = "attachment; filename=Reporte_Doctores_" + System.currentTimeMillis() + ".pdf";
    response.setHeader(cabecera, valor);

    List<Doctor> lista = doctorRepository.findAll();
    DoctorExportPDF exporter = new DoctorExportPDF(lista);
    exporter.exportar(response);
}









    // ==========================================
    // 4. GESTIÓN DE CITAS Y PDF
    // ==========================================
    @GetMapping("/citas") 
    public String mostrarCitas(Model model) {
        model.addAttribute("citas", citaRepository.findAll());
        return "Citas/gestioncitas"; 
    }

    @GetMapping("/citas/nueva")
    public String mostrarFormularioNuevaCita(Model model) {
        model.addAttribute("pacientes", pacienteRepository.findAll());
        model.addAttribute("doctores", doctorRepository.findAll());
        model.addAttribute("especialidades", especialidadRepository.findAll());
        return "Citas/nueva-cita";
    }

    @PostMapping("/citas/guardar")
    public String guardarNuevaCita(@RequestParam("fecha") String fecha, @RequestParam("idDoctor") Integer idDoctor,
                                   @RequestParam("idPaciente") Integer idPaciente, @RequestParam("idEspecialidad") Integer idEspecialidad, Model model) {
        String[] partes = fecha.split("T"); 
        LocalDate fechaCita = LocalDate.parse(partes[0]);
        LocalTime horaCita = LocalTime.parse(partes[1]);

        Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);
        Doctor doctor = doctorRepository.findById(idDoctor).orElse(null);
        Especialidad espec = especialidadRepository.findById(idEspecialidad).orElse(null);

        Cita nuevaCita = new Cita();
        nuevaCita.setFechaCita(fechaCita);
        nuevaCita.setHoraCita(horaCita);
        nuevaCita.setPaciente(paciente);
        nuevaCita.setDoctor(doctor);
        nuevaCita.setEspecialidad(espec);
        nuevaCita.setEstatus("Agendada");

        citaRepository.save(nuevaCita);
        return "redirect:/admin/citas"; 
    }

    @Transactional
    @GetMapping("/citas/eliminar/{id}")
    public String eliminarCita(@PathVariable("id") int id) {
        citaRepository.deleteById(id);
        return "redirect:/admin/citas";
    }

    @GetMapping("/citas/pdf")
    public void generarPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Citas.pdf");
        pdfService.exportar(response, citaRepository.findAll());
    }

    // --- CAMBIAR ESTATUS DE LA CITA ---
@PostMapping("/citas/cambiar-estatus")
public String cambiarEstatus(@RequestParam("id") Integer id, 
                             @RequestParam("nuevoEstatus") String nuevoEstatus,
                             RedirectAttributes flash) {
    Cita cita = citaRepository.findById(id).orElse(null);
    if (cita != null) {
        cita.setEstatus(nuevoEstatus);
        citaRepository.save(cita);
        flash.addFlashAttribute("mensajeExito", "✅ Estatus de la cita actualizado a: " + nuevoEstatus);
    }
    return "redirect:/admin/citas";
}




    //
   // --- MOSTRAR PANEL DE REPORTES ---
    @GetMapping("/reportes")
    public String mostrarReportes(Model model) {
        // Traemos los datos reales del repositorio
        List<Object[]> citasEspecialidad = citaRepository.countCitasByEspecialidad();
        List<Object[]> rankingDoctores = citaRepository.findRankingDoctores();

        // Los enviamos a la vista
        model.addAttribute("citasEspecialidad", citasEspecialidad);
        model.addAttribute("ingresosDoctores", rankingDoctores); // Mantenemos el nombre para no romper el HTML

        // Ruta de tu archivo: templates/Reportes/gestionreportes.html
        return "Reportes/gestionreportes"; 
    }

/////RECETAS
/// 
///

@GetMapping("/recetas")
public String listarRecetas(@RequestParam(name = "cedula", required = false) String cedula, Model model) {
    List<Receta> listaRecetas;
    
    // Si el usuario escribió algo en la barra de búsqueda (cédula)
    if (cedula != null && !cedula.isEmpty()) {
        listaRecetas = recetaRepository.buscarPorCedulaDoctor(cedula);
        model.addAttribute("cedulaBuscada", cedula); // Para mantener el texto en el input
    } else {
        // Si no hay búsqueda, mostramos todas
        listaRecetas = recetaRepository.findAll();
    }
    
    model.addAttribute("recetas", listaRecetas);
    return "Receta/gestionrecetas";
}

@GetMapping("/recetas/eliminar/{id}")
public String eliminarReceta(@PathVariable("id") Integer id, RedirectAttributes flash) {
    try {
        recetaRepository.deleteById(id);
        flash.addFlashAttribute("mensajeExito", "✅ Receta eliminada correctamente.");
    } catch (Exception e) {
        flash.addFlashAttribute("mensajeError", "❌ No se pudo eliminar la receta.");
    }
    return "redirect:/admin/recetas";
} 

@GetMapping("/recetas/nueva/{idCita}")
public String mostrarFormularioReceta(@PathVariable("idCita") Integer idCita, Model model) {
    Optional<Cita> citaOpt = citaRepository.findById(idCita);
    if (citaOpt.isPresent()) {
        model.addAttribute("cita", citaOpt.get());
        return "Receta/form-receta"; // Nombre del nuevo HTML
    }
    return "redirect:/admin/citas";
}

// --- GUARDAR RECETA (USANDO EL SP) ---
@PostMapping("/recetas/guardar")
public String guardarReceta(@RequestParam("idCita") Integer idCita,
                            @RequestParam("idDoctor") Integer idDoctor,
                            @RequestParam("idPaciente") Integer idPaciente,
                            @RequestParam("diagnostico") String diagnostico,
                            @RequestParam("tratamiento") String tratamiento,
                            @RequestParam("medicamentos") String medicamentos,
                            RedirectAttributes flash) {
    try {
        recetaRepository.generarReceta(idCita, idDoctor, idPaciente, diagnostico, tratamiento, medicamentos);
        flash.addFlashAttribute("mensajeExito", "✅ Receta generada y guardada exitosamente.");
    } catch (Exception e) {
        flash.addFlashAttribute("mensajeError", "❌ Error al generar la receta: " + e.getMessage());
    }
    return "redirect:/admin/recetas";
}


@GetMapping("/monitor")
public String mostrarMonitor(Model model) {
    // Obtenemos todos los logs, los más recientes primero
    List<Auditoria> logs = auditoriaRepository.findAll();
    Collections.reverse(logs); // Para que el último movimiento salga arriba
    model.addAttribute("logs", logs);
    return "admin/monitor";
}



>>>>>>> b3cee62 (Orden final: archivos movidos a la raiz y limpieza de basura)
}