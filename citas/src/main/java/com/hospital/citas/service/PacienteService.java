package com.hospital.citas.service;

import com.hospital.citas.model.Paciente;
import com.hospital.citas.model.Usuario;
import com.hospital.citas.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente buscarPorUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) return null;
        return pacienteRepository.findByUsuarioId(usuario.getId());
    }
    public void guardarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    public Paciente buscarPorId(Integer id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    public void eliminarPaciente(Integer id) {
        pacienteRepository.deleteById(id);
    }
}
