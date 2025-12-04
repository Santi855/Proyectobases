package com.hospital.citas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vw_datos_paciente")
public class VwDatosPaciente {

    @Id
    @Column(name = "id_paciente")
    private Integer idPaciente;

    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String curp;
    private LocalDate fecha_nacimiento;
    private String tipo_sangre;
    private String alergias;
    private String padecimientos_previos;
    private Double peso;
    private Double estatura;

    // Getters y Setters
    public Integer getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Integer idPaciente) { this.idPaciente = idPaciente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public LocalDate getFecha_nacimiento() { return fecha_nacimiento; }
    public void setFecha_nacimiento(LocalDate fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }

    public String getTipo_sangre() { return tipo_sangre; }
    public void setTipo_sangre(String tipo_sangre) { this.tipo_sangre = tipo_sangre; }

    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }

    public String getPadecimientos_previos() { return padecimientos_previos; }
    public void setPadecimientos_previos(String padecimientos_previos) { this.padecimientos_previos = padecimientos_previos; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Double getEstatura() { return estatura; }
    public void setEstatura(Double estatura) { this.estatura = estatura; }
}
