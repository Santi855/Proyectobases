package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hospital.citas.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequestMapping("/farmacia")
public class FarmaciaController {

    @Autowired
    private MedicamentoRepository medicamentoRepo;

    @GetMapping
    public String farmacia(Model model) {
        model.addAttribute("medicamentos", medicamentoRepo.findAll());
        return "Paciente/farmacia";
    }
}

