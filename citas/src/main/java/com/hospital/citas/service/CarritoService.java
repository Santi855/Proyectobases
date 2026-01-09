package com.hospital.citas.service;
import com.hospital.citas.model.*;
import com.hospital.citas.repository.CarritoItemRepository;;
import com.hospital.citas.repository.CarritoRepository;
import com.hospital.citas.repository.CitaRepository;
import com.hospital.citas.repository.MedicamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepo;

    @Autowired
    private CarritoItemRepository itemRepo;

    @Autowired
    private CitaRepository citaRepo;

    @Autowired
    private MedicamentoRepository medicamentoRepo;

    public Carrito obtenerCarritoActivo(Paciente paciente) {
        return carritoRepo.carritoActivo(paciente.getId());
    }
    public void agregarMedicamento(Paciente paciente, Integer idMedicamento) {

        Carrito carrito = carritoRepo.carritoActivo(paciente.getId());

        if (carrito == null) {
            carrito = new Carrito();
            carrito.setPaciente(paciente);
            carrito.setTotal(BigDecimal.ZERO);
            carrito.setPagado(false);
            carrito = carritoRepo.save(carrito);
        }

        Medicamento med = medicamentoRepo.findById(idMedicamento).orElseThrow();

        CarritoItem item = new CarritoItem();
        item.setCarrito(carrito);
        item.setTipo("MEDICAMENTO");
        item.setReferenciaId(med.getIdMedicamento());
        item.setDescripcion(med.getNombre());
        item.setPrecio(med.getPrecio());

        itemRepo.save(item);

        carrito.setTotal(carrito.getTotal().add(med.getPrecio()));
        carritoRepo.save(carrito);
    }

    public void agregarCita(Paciente paciente, Integer idCita) {

        Carrito carrito = carritoRepo.carritoActivo(paciente.getId());

        if (carrito == null) {
            carrito = new Carrito();
            carrito.setPaciente(paciente);
            carrito.setTotal(BigDecimal.ZERO);
            carrito.setPagado(false);
            carrito = carritoRepo.save(carrito);
        }

        Cita cita = citaRepo.findById(idCita).orElseThrow();

        CarritoItem item = new CarritoItem();
        item.setCarrito(carrito);
        item.setTipo("CITA");
        item.setReferenciaId(cita.getIdCita());
        item.setDescripcion("Cita con " + cita.getDoctor().getUsuario().getNombre());
        item.setPrecio(new BigDecimal("500")); // luego lo haces dinámico

        itemRepo.save(item);

        carrito.setTotal(carrito.getTotal().add(item.getPrecio()));
        carritoRepo.save(carrito);
    }
    @Transactional
    public void pagarCarrito(Paciente paciente) {

        Carrito carrito = carritoRepo.carritoActivo(paciente.getId());
        if (carrito == null) return;

        // Cambiar estatus de citas
        for (CarritoItem item : carrito.getItems()) {
            if (item.getTipo().equals("CITA")) {
                Cita cita = citaRepo.findById(item.getReferenciaId()).orElseThrow();
                cita.setEstatus("PAGADA");
                citaRepo.save(cita);
            }
        }

        carrito.setPagado(true);
        carritoRepo.save(carrito);

    }


}

