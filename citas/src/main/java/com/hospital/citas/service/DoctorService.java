package com.hospital.citas.service;
import com.hospital.citas.model.Doctor;
import com.hospital.citas.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DoctorService {



    @Autowired
    private DoctorRepository doctorRepository;

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


}