package com.hospital.citas.service;

import com.hospital.citas.model.Cita;
import com.hospital.citas.model.Receta;
import com.hospital.citas.repository.CitaRepository;
import com.hospital.citas.repository.RecetaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Transactional
    public void guardar(Receta receta) {

        recetaRepository.save(receta);

        Cita citaBD = citaRepository.findById(
                receta.getCita().getIdCita()
        ).orElseThrow();

        citaBD.setEstatus("ATENDIDA");

        citaRepository.save(citaBD);
    }
    public boolean existeRecetaParaCita(Integer idCita) {
        return recetaRepository.existsByCita_IdCita(idCita);
    }

    public Receta obtenerPorCita(Integer idCita) {
        return recetaRepository.findByCita_IdCita(idCita);
    }



    public List<Receta> obtenerPorPaciente(Integer idPaciente) {
        return recetaRepository.buscarPorPaciente(idPaciente);
    }
}
