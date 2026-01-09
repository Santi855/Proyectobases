package com.hospital.citas.controller;

import com.hospital.citas.service.RecepcionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/recepcionista") 
public class RecepcionistaController {

    // 1. Inyectar el Servicio de Recepcionista para acceder a la lógica de negocio
    @Autowired
    private RecepcionistaService recepcionistaService; 

    // --- 2. MÉTODO PARA MOSTRAR LA VISTA DEL FORMULARIO (GET) ---
    // URL: http://localhost:8080/recepcionista/mostrarCalculadora
    @GetMapping("/mostrarCalculadora")
    public String mostrarCalculadora() {
        // Devuelve la vista HTML ubicada en /templates/Recepcionista/calcular-total-form.html
        return "Recepcionista/calcular-total-form"; 
    }

    // --- 3. MÉTODO PARA PROCESAR LA SOLICITUD DE CÁLCULO (GET) ---
    // URL: http://localhost:8080/recepcionista/calcularTotal?idCita=X
    @GetMapping("/calcularTotal")
    @ResponseBody // IMPORTANTE: Indica que la respuesta es un dato (String), NO un archivo HTML
    public String calcularTotalPagar(@RequestParam("idCita") Integer idCita) {
        
        try {
            // Llama al servicio para ejecutar el Stored Procedure
            Double total = recepcionistaService.obtenerTotalPagar(idCita);
            
            // Formateamos y devolvemos el resultado al JavaScript del navegador
            return String.format("El total a pagar es: $%.2f", total);
            
        } catch (Exception e) {
            // Captura errores del SP (ej: Cita no encontrada, formato incorrecto en linea_pago)
            
            // Devolvemos el mensaje de error para mostrarlo en la web
            return "Error: " + e.getMessage();
        }
    }
}