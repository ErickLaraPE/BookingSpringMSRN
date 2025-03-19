package spring.backend.crud.lurinbooking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import spring.backend.crud.lurinbooking.services.UsuarioService;

@Component

public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String>{

    @Autowired 
    private UsuarioService usuarioService;

    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(usuarioService == null){
            return true;
        }
        return !usuarioService.existeUsuario(username);
    }
}
