
package com.api.Fitzone.services;

import com.api.Fitzone.models.*;
import com.api.Fitzone.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class GimnasioService {

    private final SocioRepository socios;
    private final ClaseRepository clases;
    private final InscripcionRepository inscripciones;

    public GimnasioService(
            SocioRepository socios,
            ClaseRepository clases,
            InscripcionRepository inscripciones) {

        this.socios = socios;
        this.clases = clases;
        this.inscripciones = inscripciones;
    }

    public List<Socio> listarSocios() {
        return socios.findAll();
    }

    public Socio guardarSocio(Socio socio) {

        if (socio.getNombre() == null ||
            socio.getNombre().isBlank() ||
            socio.getCorreo() == null ||
            socio.getCorreo().isBlank() ||
            socio.getMembresiaHasta() == null) {

            throw error(
                HttpStatus.BAD_REQUEST,
                "Nombre, correo y membresiaHasta son obligatorios"
            );
        }

        return socios.save(socio);
    }

    public Map<String, Object> consultarMembresia(Long id) {

        Socio socio = socios.findById(id)
            .orElseThrow(() -> error(
                HttpStatus.NOT_FOUND,
                "Socio no encontrado"
            ));

        return Map.of(
            "socioId", id,
            "vigente", socio.isMembresiaVigente(),
            "membresiaHasta", socio.getMembresiaHasta()
        );
    }

    public List<Clase> listarClases() {
        return clases.findAll();
    }

    public Clase guardarClase(Clase clase) {

        if (clase.getNombre() == null ||
            clase.getNombre().isBlank() ||
            clase.getSucursal() == null ||
            clase.getSucursal().isBlank() ||
            clase.getFechaHora() == null) {

            throw error(
                HttpStatus.BAD_REQUEST,
                "Nombre, sucursal y fechaHora son obligatorios"
            );
        }

        clase.setCapacidad(20);

        return clases.save(clase);
    }

    public Map<String, Object> consultarCupo(Long claseId) {

        Clase clase = clases.findById(claseId)
            .orElseThrow(() -> error(
                HttpStatus.NOT_FOUND,
                "Clase no encontrada"
            ));

        long ocupados = inscripciones.countByClaseId(claseId);

        Map<String, Object> salida = new LinkedHashMap<>();

        salida.put("claseId", claseId);
        salida.put("capacidad", clase.getCapacidad());
        salida.put("inscritos", ocupados);
        salida.put(
            "disponibles",
            Math.max(0, clase.getCapacidad() - ocupados)
        );

        return salida;
    }

    @Transactional
    public Inscripcion inscribirEnClase(Long socioId, Long claseId) {

        Clase clase = clases.bloquearClase(claseId)
            .orElseThrow(() -> error(
                HttpStatus.NOT_FOUND,
                "Clase no encontrada"
            ));

        Socio socio = socios.findById(socioId)
            .orElseThrow(() -> error(
                HttpStatus.NOT_FOUND,
                "Socio no encontrado"
            ));

        if (!socio.isMembresiaVigente()) {
            throw error(
                HttpStatus.CONFLICT,
                "Membresia vencida"
            );
        }

        if (!clase.getFechaHora().isAfter(LocalDateTime.now())) {
            throw error(
                HttpStatus.CONFLICT,
                "La clase ya inicio o termino"
            );
        }

        if (inscripciones.existsBySocioIdAndClaseId(socioId, claseId)) {
            throw error(
                HttpStatus.CONFLICT,
                "El socio ya esta inscrito en esta clase"
            );
        }

        if (inscripciones.countByClaseId(claseId)
                >= clase.getCapacidad()) {

            throw error(
                HttpStatus.CONFLICT,
                "No hay cupo disponible"
            );
        }

        Inscripcion inscripcion = new Inscripcion();

        inscripcion.setSocio(socio);
        inscripcion.setClase(clase);

        Inscripcion guardada = inscripciones.save(inscripcion);

        System.out.println(
            "CONFIRMACION: " + socio.getCorreo() +
            " se inscribio a " + clase.getNombre()
        );

        return guardada;
    }

    public List<Inscripcion> listarInscripciones() {
        return inscripciones.findAll();
    }

    public List<Inscripcion> inscripcionesDeClase(Long id) {

        if (!clases.existsById(id)) {
            throw error(
                HttpStatus.NOT_FOUND,
                "Clase no encontrada"
            );
        }

        return inscripciones.findByClaseId(id);
    }

    @Transactional
    public void cancelarInscripcion(Long id) {

        if (!inscripciones.existsById(id)) {
            throw error(
                HttpStatus.NOT_FOUND,
                "Inscripcion no encontrada"
            );
        }

        inscripciones.deleteById(id);
    }

    private ResponseStatusException error(
            HttpStatus status, String mensaje) {

        return new ResponseStatusException(status, mensaje);
    }
}
