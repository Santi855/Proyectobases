package com.hospital.citas.service;

import com.hospital.citas.model.*;
import com.hospital.citas.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
=======
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional; 
>>>>>>> b3cee62 (Orden final: archivos movidos a la raiz y limpieza de basura)

@Service
public class UsuarioService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PacienteRepository pacienteRepository;
    @Autowired private CitaRepository citaRepository;
    @Autowired private RecetaRepository recetaRepository;
    @Autowired private ConsultorioRepository consultorioRepository;
    @Autowired private DoctorRepository doctorRepository; 

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ==========================================
    // 1. MÉTODOS DE BÚSQUEDA Y VALIDACIÓN
    // ==========================================
    public Usuario buscarPorUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

<<<<<<< HEAD
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public Usuario registrarUsuario(String nombre, String apellido_paterno, String apellido_mateno, String correo, String telefono, String usuario, String contrasena, String rol) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setApellido_paterno(apellido_paterno);
        nuevo.setApellido_materno(apellido_mateno);
        nuevo.setCorreo(correo);
        nuevo.setTelefono(telefono);
        nuevo.setUsuario(usuario);
        nuevo.setRol(rol);
        nuevo.setContrasena(encoder.encode(contrasena)); // Hashea la contraseña
        return usuarioRepository.save(nuevo);
    }
=======
>>>>>>> b3cee62 (Orden final: archivos movidos a la raiz y limpieza de basura)
    public Usuario obtenerUsuarioActual(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    public boolean validarCredenciales(String usuario, String contrasena) {
        Usuario u = usuarioRepository.findByUsuario(usuario);
        if (u == null) return false;
        return encoder.matches(contrasena, u.getContrasena());
    }
<<<<<<< HEAD
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();   // ✔ Aquí regresamos todos los usuarios
    }
}
=======

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    // ==========================================
    // 2. MÉTODOS DE REGISTRO Y GUARDADO
    // ==========================================
    public Usuario registrarUsuario(String nombre, String apellido_paterno, String apellido_mateno, String correo, String telefono, String usuario, String contrasena, String rol) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setApellido_paterno(apellido_paterno);
        nuevo.setApellido_materno(apellido_mateno);
        nuevo.setCorreo(correo);
        nuevo.setTelefono(telefono);
        nuevo.setUsuario(usuario);
        nuevo.setRol(rol);
        nuevo.setContrasena(encoder.encode(contrasena));
        return usuarioRepository.save(nuevo);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // ==========================================
    // 3. LÓGICA DE ELIMINACIÓN EN CASCADA
    // ==========================================
    @Transactional
    public void eliminarUsuario(Integer id) {
        
        // --- 1. ELIMINAR PACIENTE Y SUS DEPENDENCIAS ---
        Paciente pacienteAsociado = pacienteRepository.findByUsuarioId(id);
        if (pacienteAsociado != null) {
            List<Cita> citasDelPaciente = citaRepository.findAllByPaciente_Id(pacienteAsociado.getId());
            if (!citasDelPaciente.isEmpty()) {
                for (Cita cita : citasDelPaciente) {
                    List<Receta> recetasDeCita = recetaRepository.findAllByCita_IdCita(cita.getIdCita());
                    if (!recetasDeCita.isEmpty()) {
                        recetaRepository.deleteAll(recetasDeCita); 
                    }
                }
                citaRepository.deleteAll(citasDelPaciente);
            }
            pacienteRepository.delete(pacienteAsociado);
        }
        
        // --- 2. ELIMINAR DOCTOR ASOCIADO ---
        Doctor doctorAsociado = doctorRepository.findByUsuarioId(id);
        if (doctorAsociado != null) {
            // A) NUEVO: Primero eliminar recetas emitidas por este doctor (Soluciona error FK)
            List<Receta> recetasEmitidas = recetaRepository.findAllByDoctor_IdDoctor(doctorAsociado.getIdDoctor());
            if (!recetasEmitidas.isEmpty()) {
                recetaRepository.deleteAll(recetasEmitidas);
            }

            // B) Desvincular consultorio
            Consultorio consultorioAsociado = consultorioRepository.findByDoctor_IdDoctor(doctorAsociado.getIdDoctor());
            if (consultorioAsociado != null) {
                consultorioAsociado.setDoctor(null); 
                consultorioRepository.save(consultorioAsociado); 
            }
            
            // C) Eliminar registro del doctor
            doctorRepository.delete(doctorAsociado); 
        }
        
        // --- 3. ELIMINAR EL REGISTRO DE USUARIO ---
        usuarioRepository.deleteById(id);
    }
}
>>>>>>> b3cee62 (Orden final: archivos movidos a la raiz y limpieza de basura)
