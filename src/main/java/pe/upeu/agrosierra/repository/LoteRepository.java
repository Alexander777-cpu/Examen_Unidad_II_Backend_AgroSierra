package pe.upeu.agrosierra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.upeu.agrosierra.entity.Lote;

import java.util.Optional;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

    Optional<Lote> findByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCase(String codigo);

    @Query(value = """
        SELECT l FROM Lote l
        WHERE (:valle IS NULL OR LOWER(l.valle) = LOWER(:valle))
        """,
            countQuery = """
        SELECT COUNT(l) FROM Lote l
        WHERE (:valle IS NULL OR LOWER(l.valle) = LOWER(:valle))
        """)
    Page<Lote> buscar(@Param("valle") String valle, Pageable pageable);
}