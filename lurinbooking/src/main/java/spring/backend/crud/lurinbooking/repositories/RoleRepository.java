package spring.backend.crud.lurinbooking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.backend.crud.lurinbooking.entities.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{
    Optional<Role> findByName(String name);
}
