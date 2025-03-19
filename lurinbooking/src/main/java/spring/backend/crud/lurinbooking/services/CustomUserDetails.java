package spring.backend.crud.lurinbooking.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{
    private final String username;
    private final String name;
    private final String password;
    private final Integer id; // Agrega el campo para el ID
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String name,String password, Integer id, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.id = id; // Inicializa el ID
        this.authorities = authorities;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id; // Método para obtener el ID
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password; // No devolvemos el password
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getName() {
        return name;
    }
}
