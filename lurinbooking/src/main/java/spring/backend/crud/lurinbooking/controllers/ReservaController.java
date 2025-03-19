package spring.backend.crud.lurinbooking.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.backend.crud.lurinbooking.DTO.ReservaDTO;
import spring.backend.crud.lurinbooking.entities.Ambiente;
import spring.backend.crud.lurinbooking.entities.Reserva;
import spring.backend.crud.lurinbooking.repositories.AmbienteRepository;
import spring.backend.crud.lurinbooking.repositories.ReservaRepository;
import spring.backend.crud.lurinbooking.repositories.UsuarioRepository;
import spring.backend.crud.lurinbooking.services.ReservaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AmbienteRepository ambienteRepository;

    @GetMapping("/{reservaId}")
    public ResponseEntity<Reserva> muestraReserva (@PathVariable("reservaId") Integer reservaId) {
        Reserva reservaF = reservaService.buscaReservaXId(reservaId).orElseThrow();
        if(reservaF == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservaF);
    }

    @GetMapping("/calculaTotal/{reservaId}")
    public ResponseEntity<Map<String,Integer>> calculodelTotal(@PathVariable("reservaId") Integer reservaId) {
        Reserva reservaF = reservaService.buscaReservaXId(reservaId).orElseThrow();
        if(reservaF == null){
            return ResponseEntity.notFound().build();
        }
        Map<String, Integer> response = new HashMap<>();
        response.put("total", reservaService.calculaTotalReserva(reservaId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/") // corregir el usuario
    public ResponseEntity<Reserva> savingReserva (@RequestBody ReservaDTO reservaDTO) {
        
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuarioRepository.findById(reservaDTO.getUsuario_id()).orElseThrow());

        List<Ambiente> ambientesF = ambienteRepository.findAllById(reservaDTO.getAmbientes_id());
        reserva.setAmbientes(ambientesF);

        reserva.setTipoAmbReserva(reservaDTO.getTipoAmbReserva());
        reserva.setTipoPago(reservaDTO.getTipoPago());

        return ResponseEntity.ok(reservaService.guardaReserva(reserva));
    }

    @PostMapping("/buscarReservasXCriterios/")
    public ResponseEntity<List<Reserva>> buscandoReservasXCriterios(@RequestBody ReservaDTO reservaDTO ) {
        List<Reserva> reservasF = reservaService.buscaReservasXCriterios(reservaDTO.getFechaCreacion(), reservaDTO.getTipoPago(), reservaDTO.getTipoAmbReserva(),reservaDTO.getUsuario_id());
        if(reservasF.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservasF);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> obtengaReservasPorUsuario(@PathVariable("usuarioId") Integer usuarioId) {
        List<Reserva> reservasF = reservaService.buscaReservasXUsuario(usuarioId);
        if(reservasF.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservasF);
    }

    @PutMapping("/{reservaId}")
    public ResponseEntity<Reserva> updatingReserva(@PathVariable("reservaId") Integer reservaId, @RequestBody ReservaDTO reservaDTO) {

        Reserva reservaActual = reservaRepository.findById(reservaId).orElseThrow();
        
        if(reservaDTO.getAmbientes_id() != null){
            List<Ambiente> ambientesF = ambienteRepository.findAllById(reservaDTO.getAmbientes_id());
            reservaActual.setAmbientes(ambientesF);
        }
        if(reservaDTO.getFechaCreacion() != null){
            reservaActual.setFechaCreacion(reservaDTO.getFechaCreacion());
        }
        if(reservaDTO.getTipoAmbReserva() != null){
            reservaActual.setTipoAmbReserva(reservaDTO.getTipoAmbReserva());
        }
        if(reservaDTO.getTipoPago() != null){
            reservaActual.setTipoPago(reservaDTO.getTipoPago());
        }

        Reserva reservaActualizada = reservaService.actualizaReserva(reservaActual);
        return ResponseEntity.ok(reservaActualizada);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{reservaId}")
    public ResponseEntity<?> deletingReserva(@PathVariable("reservaId") Integer reservaId){
       Optional<Reserva> reservaEliminada = reservaService.eliminaReserva(reservaId);
        if(reservaEliminada.isPresent()) {
           return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
