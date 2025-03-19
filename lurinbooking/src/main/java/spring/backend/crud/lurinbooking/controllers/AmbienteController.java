package spring.backend.crud.lurinbooking.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import spring.backend.crud.lurinbooking.entities.Ambiente;
import spring.backend.crud.lurinbooking.services.AmbienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/ambientes")
public class AmbienteController {

    @Autowired
    private AmbienteService ambienteService;

    @GetMapping("/")
    public ResponseEntity<List<Ambiente>> buscarAmbientes() {
        List<Ambiente> ambientesF= ambienteService.buscaAmbientes();
        if(ambientesF.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ambientesF);
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> savingAmbiente (@Valid @RequestBody Ambiente ambiente,BindingResult result) {

        if(result.hasFieldErrors()){
            return validation(result);
        }

        ambienteService.guardaAmbiente(ambiente);

        Map<String,Object> response = new HashMap<>();
        response.put("msg", "Se ha registrado correctamente el ambiente");
        response.put("errorFlag",false);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(),"El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        errors.put("errorFlag",true);
        return ResponseEntity.badRequest().body(errors);
    }
    
}
