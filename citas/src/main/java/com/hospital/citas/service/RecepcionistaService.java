package com.hospital.citas.service;

import com.hospital.citas.repository.RecepcionistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 1. Importar Transactional

@Service
public class RecepcionistaService {

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;
    
    /**
     * Obtiene el total a pagar por una cita ejecutando el Stored Procedure.
     * @param idCita ID de la cita a calcular.
     * @return El monto total.
     */
    // 2. Aplicar la anotación para permitir la ejecución del @Procedure
    @Transactional(readOnly = true) 
    public Double obtenerTotalPagar(Integer idCita) {
        // Llama al repositorio, que ejecuta el SP (SP_CalcularTotalPagar)
        return recepcionistaRepository.calcularTotalPagar(idCita);
    }
}