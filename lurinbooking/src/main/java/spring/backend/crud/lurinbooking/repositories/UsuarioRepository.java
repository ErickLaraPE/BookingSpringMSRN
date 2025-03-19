package spring.backend.crud.lurinbooking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.backend.crud.lurinbooking.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Boolean existsByUsername(String username);
    Optional<Usuario> findByUsername(String username);
}
