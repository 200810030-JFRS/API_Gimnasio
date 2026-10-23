
package com.api.Fitzone.repositories;

import com.api.Fitzone.models.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InscripcionRepository
        extends JpaRepository<Inscripcion, Long> {

    long countByClaseId(Long claseId);

    boolean existsBySocioIdAndClaseId(Long socioId, Long claseId);

    List<Inscripcion> findByClaseId(Long claseId);
}
