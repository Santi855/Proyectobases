package com.hospital.citas.service;

import com.hospital.citas.model.Especialidad;
import com.hospital.citas.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public List<Especialidad> findAll() {
        return especialidadRepository.findAll();
    }

    public Especialidad buscarPorId(Integer id) {
        return especialidadRepository.findById(id).orElse(null);
    }
}