package spring.backend.crud.lurinbooking.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.backend.crud.lurinbooking.entities.Ambiente;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente,Integer> {

}
