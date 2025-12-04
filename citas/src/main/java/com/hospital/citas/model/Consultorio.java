package com.hospital.citas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Consultorios")
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultorio")
    private Integer idConsultorio;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    // 🔹 Relación con Doctor (cada consultorio puede tener un doctor asignado)
    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    // 🧩 Constructores
    public Consultorio() {
    }

    public Consultorio(String nombre, String ubicacion, Doctor doctor) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.doctor = doctor;
    }

    // 🧾 Getters y Setters
    public Integer getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(Integer idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}