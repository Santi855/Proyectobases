package com.hospital.citas.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "Doctores")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doctor")
    private Integer idDoctor;

    // Cambiado a OneToOne con Cascade para evitar el error de eliminación
    @OneToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false)
    private Especialidad especialidad;

    @Column(name = "cedula_profesional", unique = true, nullable = false)
    private String cedulaProfesional;

    @Column(name = "horario_inicio")
    private LocalTime horarioInicio;

    @Column(name = "horario_fin")
    private LocalTime horarioFin;

    // ======= Getters y Setters =======
    public Integer getIdDoctor() { return idDoctor; }
    public void setIdDoctor(Integer idDoctor) { this.idDoctor = idDoctor; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }

    public String getCedulaProfesional() { return cedulaProfesional; }
    public void setCedulaProfesional(String cedulaProfesional) { this.cedulaProfesional = cedulaProfesional; }

    public LocalTime getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(LocalTime horarioInicio) { this.horarioInicio = horarioInicio; }

    public LocalTime getHorarioFin() { return horarioFin; }
    public void setHorarioFin(LocalTime horarioFin) { this.horarioFin = horarioFin; }

    public String getNombreCompleto() {
        if (usuario != null) {
            return usuario.getNombre() + " " + usuario.getApellido_paterno();
        }
        return "Sin nombre";
    }

    // ======= Constructores =======

public Doctor() {
    this.usuario = new Usuario();
    this.especialidad = new Especialidad();
}

}