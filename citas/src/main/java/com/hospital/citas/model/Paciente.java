package com.hospital.citas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    private String curp;
    private LocalDate fechaNacimiento;
    private String tipoSangre;
    private String alergias;
    private String padecimientosPrevios;
    private Double peso;
    private Double estatura;

    // === Getters y Setters ===
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTipoSangre() { return tipoSangre; }
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }

    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }

    public String getPadecimientosPrevios() { return padecimientosPrevios; }
    public void setPadecimientosPrevios(String padecimientosPrevios) { this.padecimientosPrevios = padecimientosPrevios; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Double getEstatura() { return estatura; }
    public void setEstatura(Double estatura) { this.estatura = estatura; }
}
