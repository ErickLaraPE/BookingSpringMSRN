package spring.backend.crud.lurinbooking.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.backend.crud.lurinbooking.entities.Reserva;
import spring.backend.crud.lurinbooking.entities.Usuario;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    public List<Reserva> findByUsuario(Usuario usuario);

    @Query("SELECT p FROM Reserva p WHERE (:fechaCreacion IS NULL OR p.fechaCreacion = :fechaCreacion) " +
            "AND (:tipoPago IS NULL OR p.tipoPago = :tipoPago) " +
            "AND (:tipoAmbReserva IS NULL OR p.tipoAmbReserva = :tipoAmbReserva)" +
            "AND (p.usuario.usuarioId = :usuario_id)")
    public List<Reserva> buscarReservasCriterios(@Param("fechaCreacion") LocalDate fechaCreacion, @Param("tipoPago") String tipoPago, @Param("tipoAmbReserva") String tipoAmbReserva,@Param("usuario_id") Integer usuario_id);

}
