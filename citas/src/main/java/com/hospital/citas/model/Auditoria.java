package com.hospital.citas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLog;

    private LocalDateTime fechaHora;
    private String usuarioResponsable;
    private String accion;
    private String detalles;

    public Auditoria() {
        this.fechaHora = LocalDateTime.now();
    }

    // Constructor rápido para usar en el controlador
    public Auditoria(String usuario, String accion, String detalles) {
        this.fechaHora = LocalDateTime.now();
        this.usuarioResponsable = usuario;
        this.accion = accion;
        this.detalles = detalles;
    }

    // Getters y Setters...
    public Integer getIdLog() { return idLog; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getUsuarioResponsable() { return usuarioResponsable; }
    public void setUsuarioResponsable(String ur) { this.usuarioResponsable = ur; }
    public String getAccion() { return accion; }
    public void setAccion(String a) { this.accion = a; }
    public String getDetalles() { return detalles; }
    public void setDetalles(String d) { this.detalles = d; }
}