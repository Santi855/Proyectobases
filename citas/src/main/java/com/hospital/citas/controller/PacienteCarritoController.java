package com.hospital.citas.controller;
import com.hospital.citas.model.Paciente;
import com.hospital.citas.model.Usuario;
import com.hospital.citas.service.CarritoService;
import com.hospital.citas.service.PacienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Paciente/carrito")
public class PacienteCarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/agregar-cita/{idCita}")
    public String agregarCita(@PathVariable Integer idCita,
                              HttpSession session,
                              RedirectAttributes ra) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        Paciente paciente = pacienteService.buscarPorUsuario(usuario);

        carritoService.agregarCita(paciente, idCita);

        ra.addFlashAttribute("mensaje", "Cita agregada al carrito");
        return "redirect:/Paciente/home";
    }
    @GetMapping("/agregar-medicamento/{id}")
    public String agregarMedicamento(@PathVariable Integer id,
                                     HttpSession session,
                                     RedirectAttributes ra) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        Paciente paciente = pacienteService.buscarPorUsuario(usuario);

        carritoService.agregarMedicamento(paciente, id);

        ra.addFlashAttribute("mensaje", "Medicamento agregado");
        return "redirect:/farmacia";
    }
    @GetMapping("/pagar")
    public String pagar(HttpSession session, RedirectAttributes ra) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        Paciente paciente = pacienteService.buscarPorUsuario(usuario);

        carritoService.pagarCarrito(paciente);

        ra.addFlashAttribute("mensaje", "Pago realizado con éxito");
        return "redirect:/Paciente/home";
    }

}

