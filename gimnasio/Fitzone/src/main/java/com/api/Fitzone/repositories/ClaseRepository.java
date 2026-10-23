
package com.api.Fitzone.repositories;

import com.api.Fitzone.models.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface ClaseRepository extends JpaRepository<Clase, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Clase c where c.id = :id")
    Optional<Clase> bloquearClase(@Param("id") Long id);
}
