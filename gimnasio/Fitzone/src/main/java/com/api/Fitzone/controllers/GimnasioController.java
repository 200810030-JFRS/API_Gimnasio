
package com.api.Fitzone.controllers;

import com.api.Fitzone.models.Clase;
import com.api.Fitzone.models.Inscripcion;
import com.api.Fitzone.models.Socio;
import com.api.Fitzone.services.GimnasioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GimnasioController {

    private final GimnasioService servicio;

    public GimnasioController(GimnasioService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/socios")
    public List<Socio> socios() {
        return servicio.listarSocios();
    }

    @PostMapping("/socios")
    @ResponseStatus(HttpStatus.CREATED)
    public Socio crearSocio(@RequestBody Socio socio) {
        return servicio.guardarSocio(socio);
    }

    @GetMapping("/socios/{id}/membresia")
    public Map<String, Object> membresia(@PathVariable Long id) {
        return servicio.consultarMembresia(id);
    }

    @GetMapping("/clases")
    public List<Clase> clases() {
        return servicio.listarClases();
    }

    @PostMapping("/clases")
    @ResponseStatus(HttpStatus.CREATED)
    public Clase crearClase(@RequestBody Clase clase) {
        return servicio.guardarClase(clase);
    }

    @GetMapping("/clases/{id}/cupo")
    public Map<String, Object> cupo(@PathVariable Long id) {
        return servicio.consultarCupo(id);
    }

    @PostMapping("/inscripciones")
    @ResponseStatus(HttpStatus.CREATED)
    public Inscripcion inscribir(@RequestBody SolicitudInscripcion solicitud) {
        return servicio.inscribirEnClase(
            solicitud.socioId(),
            solicitud.claseId()
        );
    }

    @GetMapping("/inscripciones")
    public List<Inscripcion> inscripciones() {
        return servicio.listarInscripciones();
    }

    @GetMapping("/clases/{id}/inscripciones")
    public List<Inscripcion> inscripcionesClase(@PathVariable Long id) {
        return servicio.inscripcionesDeClase(id);
    }

    @DeleteMapping("/inscripciones/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long id) {
        servicio.cancelarInscripcion(id);
    }

    public record SolicitudInscripcion(Long socioId, Long claseId) {
    }
}
