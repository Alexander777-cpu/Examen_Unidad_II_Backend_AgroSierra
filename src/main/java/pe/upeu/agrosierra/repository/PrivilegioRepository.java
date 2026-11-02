package pe.upeu.agrosierra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.upeu.agrosierra.entity.security.Privilegio;

import java.util.Optional;

@Repository
public interface PrivilegioRepository extends JpaRepository<Privilegio, Long> {

    Optional<Privilegio> findByNombreIgnoreCase(String nombre);
}