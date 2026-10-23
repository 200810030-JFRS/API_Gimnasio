
package com.api.Fitzone.services;

import com.api.Fitzone.models.Inscripcion;
import com.api.Fitzone.repositories.InscripcionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class RecordatorioService {

    private final InscripcionRepository repositorio;

    public RecordatorioService(InscripcionRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void enviarRecordatorios() {

        LocalDateTime ahora = LocalDateTime.now();

        for (Inscripcion i : repositorio.findAll()) {

            LocalDateTime inicio = i.getClase().getFechaHora();

            if (!i.isRecordatorioEnviado() &&
                !inicio.isBefore(ahora) &&
                !inicio.isAfter(ahora.plusHours(2))) {

                System.out.println(
                    "RECORDATORIO: " +
                    i.getSocio().getCorreo() +
                    " - clase " +
                    i.getClase().getNombre() +
                    " a las " + inicio
                );

                i.setRecordatorioEnviado(true);
            }
        }
    }
}
