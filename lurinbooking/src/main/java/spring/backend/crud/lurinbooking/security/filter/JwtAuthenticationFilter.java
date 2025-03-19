package spring.backend.crud.lurinbooking.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.backend.crud.lurinbooking.entities.Usuario;
import spring.backend.crud.lurinbooking.services.CustomUserDetails;

import static spring.backend.crud.lurinbooking.security.TokenJwtConfig.*;


// Filtro para validar los datos ingresados de user y password y a la vez generar el JWT (token)

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }  

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response)
        throws AuthenticationException {
        
        Usuario usuario = null;
        String username = null;
        String password = null;

        try {
                // se ingresa el username y el password del usuario
                usuario = new ObjectMapper().readValue(request.getInputStream(),Usuario.class);
                username = usuario.getUsername();
                password = usuario.getPassword();
        } catch (StreamReadException e) { 
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // se regresa el token en caso se encontro al usuario
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    // Metodo llamado automaticamente (Successful Authentication)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        
        //org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

        CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();

        String username = user.getUsername();
        String name = user.getName();
        Integer id = user.getId();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        
        Claims claims = Jwts.claims().add("authorities",new ObjectMapper().writeValueAsString(roles)).build();

        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();
        
        response.addHeader(HEADER_AUTHORIZATION,PREFIX_TOKEN  + token);
        
        Map<String, Object> body = new HashMap<>();
        body.put("token",token);
        body.put("username",username);
        body.put("name",name);
        body.put("id",id);
        body.put("errorFlag",false);
        body.put("message",String.format("Hola %s, has iniciado sesion con exito!",name));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);
    }

    // Metodo llamado automaticamente (Unsuccessful Authentication)
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
                
        Map<String, Object> body = new HashMap<>();
        body.put("message","Error en la autenticacion, username o password incorrectos!");   
        body.put("errorFlag", true);
        body.put("errorType",failed.getMessage());
        
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(401);
    }
}
