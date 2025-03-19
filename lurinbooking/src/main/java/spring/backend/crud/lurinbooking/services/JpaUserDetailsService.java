package spring.backend.crud.lurinbooking.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.backend.crud.lurinbooking.entities.Usuario;
import spring.backend.crud.lurinbooking.repositories.UsuarioRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<Usuario> userOptional = usuarioRepository.findByUsername(username);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema!",username));
        }
        // User del paquete entities
        Usuario user = userOptional.orElseThrow();
        String password = user.getPassword();
        Integer userId = user.getUsuarioId();
        String name = user.getName();

        // se obtienen los roles del tipo granted authority, para poder devolver el objeto User pero del paquete userdetails
        List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

        CustomUserDetails customUser = new CustomUserDetails(username,name,password,userId, authorities); 

        //return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),true,true,true,true,authorities); 
        return customUser;
    }
}
