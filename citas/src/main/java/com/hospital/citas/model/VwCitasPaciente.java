package com.hospital.citas.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vw_citas_paciente")
public class VwCitasPaciente {

    @Id
    @Column(name = "id_cita")
    private Integer idCita;

    @Column(name = "id_paciente")
    private Integer idPaciente;

    @Column(name = "paciente")
    private String paciente;

    @Column(name = "doctor")
    private String doctor;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "fecha_cita")
    private LocalDate fechaCita;

    @Column(name = "hora_cita")
    private LocalTime horaCita;

    @Column(name = "estatus")
    private String estatus;

    @Column(name = "linea_pago")
    private String lineaPago;

    // === Getters y Setters ===
    public Integer getIdCita() { return idCita; }
    public void setIdCita(Integer idCita) { this.idCita = idCita; }

    public Integer getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Integer idPaciente) { this.idPaciente = idPaciente; }

    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public LocalDate getFechaCita() { return fechaCita; }
    public void setFechaCita(LocalDate fechaCita) { this.fechaCita = fechaCita; }

    public LocalTime getHoraCita() { return horaCita; }
    public void setHoraCita(LocalTime horaCita) { this.horaCita = horaCita; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    public String getLineaPago() { return lineaPago; }
    public void setLineaPago(String lineaPago) { this.lineaPago = lineaPago; }
}
