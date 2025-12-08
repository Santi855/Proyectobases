package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/home")
    public String home() {
        return "admin/home";
    }
    @GetMapping("Doctores/crdoctores")
    public String crdoctores(){
        return "admin/Doctores/crdoctores";
    }

}