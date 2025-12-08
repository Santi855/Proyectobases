package com.hospital.citas.config;

import com.hospital.citas.model.Paciente;
import com.hospital.citas.model.Usuario;
import com.hospital.citas.repository.PacienteRepository;
import com.hospital.citas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepository,
                               PacienteRepository pacienteRepository) {
        return args -> {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            System.out.println("=== Cargando datos iniciales de HospiCitas ===");

            if (usuarioRepository.findByUsuario("admin") == null) {
                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setApellido_paterno("Principal");
                admin.setApellido_materno("Admin");
                admin.setUsuario("admin");
                admin.setCorreo("admin@hospital.com");
                admin.setTelefono("555-000-0000");
                admin.setRol("Admin");
                admin.setContrasena(encoder.encode("admin123"));
                usuarioRepository.save(admin);

                System.out.println("✔ Usuario administrador creado.");
            }


            if (usuarioRepository.findByUsuario("jperez") == null) {
                Usuario pacienteUsuario = new Usuario();
                pacienteUsuario.setNombre("Juan");
                pacienteUsuario.setApellido_paterno("Pérez");
                pacienteUsuario.setApellido_materno("Rodriguez");
                pacienteUsuario.setUsuario("jperez");
                pacienteUsuario.setCorreo("juan@correo.com");
                pacienteUsuario.setTelefono("555-111-1111");
                pacienteUsuario.setRol("Paciente");
                pacienteUsuario.setContrasena(encoder.encode("1234"));
                usuarioRepository.save(pacienteUsuario);

                Paciente paciente = new Paciente();
                paciente.setUsuario(pacienteUsuario);
                paciente.setCurp("JUAP900101HDFRNL05");
                paciente.setFechaNacimiento(java.time.LocalDate.of(1990, 1, 1));
                paciente.setTipoSangre("O+");
                paciente.setAlergias("Ninguna");
                paciente.setPadecimientosPrevios("Ninguno");
                paciente.setPeso(70.5);
                paciente.setEstatura(1.75);
                pacienteRepository.save(paciente);

                System.out.println("✔ Paciente de ejemplo creado.");
            }

            System.out.println("=== Datos iniciales cargados exitosamente ===");
        };
    }
}
