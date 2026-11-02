package pe.upeu.agrosierra.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.upeu.agrosierra.entity.security.Rol;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    @EntityGraph(attributePaths = {"privilegios"})
    Optional<Rol> findByNombreIgnoreCase(String nombre);
}