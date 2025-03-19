package spring.backend.crud.lurinbooking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.backend.crud.lurinbooking.entities.Role;
import spring.backend.crud.lurinbooking.entities.Usuario;
import spring.backend.crud.lurinbooking.repositories.RoleRepository;
import spring.backend.crud.lurinbooking.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<Usuario> buscaUsuario(String usuarioName){
        return usuarioRepository.findByUsername(usuarioName);
    }

    public Boolean existeUsuario (String username){
        return usuarioRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario guardaUsuario(Usuario usuario){
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        // reservas vacias List<Reserva> reservas = new ArrayList<>();
        optionalRoleUser.ifPresent(role->roles.add(role));
        if(usuario.isAdmin()){
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(role->roles.add(role));
        }
        usuario.setRoles(roles); // se guardan los roles al nuevo usuario
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // se encripta su password
        return usuarioRepository.save(usuario);
    }
}
