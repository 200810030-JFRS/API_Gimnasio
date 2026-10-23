
package com.api.Fitzone.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"socio_id", "clase_id"}
    )
)
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Socio socio;

    @ManyToOne(optional = false)
    private Clase clase;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    private boolean recordatorioEnviado = false;

    public Long getId() {
        return id;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public boolean isRecordatorioEnviado() {
        return recordatorioEnviado;
    }

    public void setRecordatorioEnviado(boolean enviado) {
        this.recordatorioEnviado = enviado;
    }
}
