package com.hospital.citas.service;

import com.hospital.citas.model.Usuario;
import com.hospital.citas.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Usuario buscarPorUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public Usuario registrarUsuario(String nombre, String apellido, String correo, String telefono, String usuario, String contrasena, String rol) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setApellido(apellido);
        nuevo.setCorreo(correo);
        nuevo.setTelefono(telefono);
        nuevo.setUsuario(usuario);
        nuevo.setRol(rol);
        nuevo.setContrasena(encoder.encode(contrasena)); // Hashea la contraseña
        return usuarioRepository.save(nuevo);
    }
    public Usuario obtenerUsuarioActual(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }


    public boolean validarCredenciales(String usuario, String contrasena) {
        Usuario u = usuarioRepository.findByUsuario(usuario);
        if (u == null) return false;
        return encoder.matches(contrasena, u.getContrasena());
    }
}
