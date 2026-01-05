package com.hospital.citas.service;

import com.hospital.citas.model.Doctor;
import com.hospital.citas.model.Usuario;
import com.hospital.citas.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UsuarioService usuarioService; // ✔ inyectado correctamente

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }



    public Doctor buscarPorId(Integer id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public List<Doctor> obtenerDoctoresPorEspecialidad(Integer idEspecialidad) {
        return doctorRepository.findByEspecialidad(idEspecialidad);
    }

    public Doctor guardar(Doctor doctor) {
        Usuario usuario = doctor.getUsuario();
        usuario.setRol("Doctor");
        usuarioService.guardar(usuario);
        return doctorRepository.save(doctor);

<<<<<<< HEAD
    }
=======
    public Doctor guardar(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public boolean eliminar(Integer idDoctor) {
        int pendientes = doctorRepository.contarCitasPendientes(idDoctor);
        if (pendientes > 0) {
            return false; // no se puede eliminar
        }
        doctorRepository.deleteById(idDoctor);
        return true;
    }

>>>>>>> b3cee62 (Orden final: archivos movidos a la raiz y limpieza de basura)

    public boolean eliminar(Integer idDoctor) {

        int pendientes = doctorRepository.contarCitasPendientes(idDoctor);
        if (pendientes > 0) {
            return false;
        }

        Doctor doctor = doctorRepository.findById(idDoctor).orElse(null);
        if (doctor == null) {
            return false;
        }

        Usuario usuario = doctor.getUsuario();
        usuario.setRol("Doctor Inactivo");

        usuarioService.guardar(usuario);

        doctorRepository.deleteById(idDoctor);

        return true;
    }
}
