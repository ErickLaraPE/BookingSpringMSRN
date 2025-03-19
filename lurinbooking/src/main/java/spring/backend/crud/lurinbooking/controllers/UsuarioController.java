package spring.backend.crud.lurinbooking.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import spring.backend.crud.lurinbooking.entities.Usuario;
import spring.backend.crud.lurinbooking.services.CustomUserDetails;
import spring.backend.crud.lurinbooking.services.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> obtainUsers() {
        List<Usuario> usuariosF = usuarioService.listarUsuarios();
        if(usuariosF.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuariosF);
    }
    
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario,BindingResult result) {
        if(result.hasFieldErrors()){
            return validation(result);
        }
        usuarioService.guardaUsuario(usuario);
        Map<String,Object> response = new HashMap<>();
        response.put("msg", "Se ha registrado correctamente el usuario");
        response.put("errorFlag",false);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario usuario,BindingResult result) {
        usuario.setAdmin(false);
        return create(usuario,result);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(),"El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        errors.put("errorFlag",true);
        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> obtenerPerfil(@AuthenticationPrincipal CustomUserDetails usuario) {
        return ResponseEntity.ok(usuario);
    }
    
}
