package com.hospital.citas.service;

import com.hospital.citas.model.VwDatosPaciente;
import com.hospital.citas.repository.VwDatosPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwDatosPacienteService {
    @Autowired
    private VwDatosPacienteRepository repo;

    public VwDatosPaciente obtenerDatosPorIdPaciente(Integer idPaciente) {
        return repo.findById(idPaciente).orElse(null);
    }

}
