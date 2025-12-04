package com.hospital.citas.service;

import com.hospital.citas.model.Consultorio;
import com.hospital.citas.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {

    @Autowired
    private ConsultorioRepository consultorioRepository;

    public List<Consultorio> listarConsultorios() {
        return consultorioRepository.findAll();
    }

    public Consultorio buscarPorId(Integer id) {
        return consultorioRepository.findById(id).orElse(null);
    }

    // Retorna el consultorio asociado a un doctor
    public Consultorio obtenerConsultorioPorDoctor(Integer idDoctor) {
        return consultorioRepository.findByDoctor_IdDoctor(idDoctor);
    }
}
