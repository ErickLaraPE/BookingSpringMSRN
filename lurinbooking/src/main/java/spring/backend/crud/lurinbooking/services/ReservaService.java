package spring.backend.crud.lurinbooking.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.backend.crud.lurinbooking.entities.Ambiente;
import spring.backend.crud.lurinbooking.entities.Reserva;
import spring.backend.crud.lurinbooking.entities.Usuario;
import spring.backend.crud.lurinbooking.repositories.ReservaRepository;
import spring.backend.crud.lurinbooking.repositories.UsuarioRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public Optional<Reserva> buscaReservaXId(Integer reservaId){
        return reservaRepository.findById(reservaId);
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscaReservasXCriterios(LocalDate fechaCreacion, String tipoPago, String tipoAmbReserva,Integer usuario_id){
        return reservaRepository.buscarReservasCriterios(fechaCreacion, tipoPago, tipoAmbReserva,usuario_id);
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscaReservasXUsuario(Integer usuarioId){
        Usuario usuarioF = usuarioRepository.findById(usuarioId).orElseThrow();
        List<Reserva> reservasF = reservaRepository.findByUsuario(usuarioF);
        return reservasF;
    }

    @Transactional
    public Reserva actualizaReserva(Reserva reserva){
        return reservaRepository.save(reserva);
    }

    @Transactional
    public Optional<Reserva> eliminaReserva( Integer reservaId){
        Optional <Reserva> reserva = reservaRepository.findById(reservaId);
        reserva.ifPresent(reservaDb->{
            reservaRepository.delete(reservaDb);
        });
        return reserva;
    }

    @Transactional
    public Reserva guardaReserva(Reserva reserva){
        return reservaRepository.save(reserva);
    }

    @Transactional(readOnly = true)
    public Integer calculaTotalReserva(Integer reservaId){
        Reserva reserva = reservaRepository.findById(reservaId).orElseThrow();
        List<Ambiente> listaAmb = reserva.getAmbientes();
        Integer total = listaAmb.stream().map(amb -> amb.getPrice()).reduce(0, (a, b) -> a + b);
        return total;
    }
}
