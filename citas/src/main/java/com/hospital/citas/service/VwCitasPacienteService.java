package com.hospital.citas.service;

import com.hospital.citas.model.VwCitasPaciente;
import com.hospital.citas.repository.VwCitasPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class VwCitasPacienteService {


    @Autowired
    private VwCitasPacienteRepository repo;

    public List<VwCitasPaciente> obtenerCitasPorPaciente(Integer idPaciente) {
        List<VwCitasPaciente> citas = repo.findByIdPaciente(idPaciente);
        return (citas != null) ? citas : Collections.emptyList();
    }
}


