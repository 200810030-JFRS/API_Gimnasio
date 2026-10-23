
package com.api.Fitzone.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private LocalDate membresiaHasta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getMembresiaHasta() {
        return membresiaHasta;
    }

    public void setMembresiaHasta(LocalDate fecha) {
        this.membresiaHasta = fecha;
    }

    public boolean isMembresiaVigente() {
        return membresiaHasta != null &&
               !membresiaHasta.isBefore(LocalDate.now());
    }
}
