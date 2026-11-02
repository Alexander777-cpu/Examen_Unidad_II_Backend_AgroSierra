package pe.upeu.agrosierra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.upeu.agrosierra.entity.Labor;
import pe.upeu.agrosierra.enums.TipoLabor;

import java.util.Optional;

@Repository
public interface LaborRepository extends JpaRepository<Labor, Long> {

    @EntityGraph(attributePaths = {"lote"})
    @Query(value = """
        SELECT l FROM Labor l
        WHERE l.lote.id = :loteId
        ORDER BY l.fecha DESC, l.fechaRegistro DESC
        """,
            countQuery = """
        SELECT COUNT(l) FROM Labor l
        WHERE l.lote.id = :loteId
        """)
    Page<Labor> findByLoteIdOrderByFechaDesc(@Param("loteId") Long loteId, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"lote"})
    Optional<Labor> findById(Long id);

    boolean existsByLoteIdAndTipo(Long loteId, TipoLabor tipo);
}