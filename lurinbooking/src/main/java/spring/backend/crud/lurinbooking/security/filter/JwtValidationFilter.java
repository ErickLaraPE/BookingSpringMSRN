package spring.backend.crud.lurinbooking.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.backend.crud.lurinbooking.security.SimpleGrantedAuthorityJsonCreator;

import static spring.backend.crud.lurinbooking.security.TokenJwtConfig.CONTENT_TYPE;
import static spring.backend.crud.lurinbooking.security.TokenJwtConfig.HEADER_AUTHORIZATION;
import static spring.backend.crud.lurinbooking.security.TokenJwtConfig.PREFIX_TOKEN;
import static spring.backend.crud.lurinbooking.security.TokenJwtConfig.SECRET_KEY;

// Filtro para validar si el token ingresado en la cabecera es valido y correcto

public class JwtValidationFilter extends BasicAuthenticationFilter{

    public JwtValidationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                // se obtiene la cabecera o header
                String header = request.getHeader(HEADER_AUTHORIZATION);
                if(header == null || !header.startsWith(PREFIX_TOKEN)){
                    chain.doFilter(request, response);
                    return;
                }
                // se extrae el token
                String token = header.replaceFirst(PREFIX_TOKEN, "");
                try {
                    // se extrae los claims en donde se encuentra la informacion de la entidad
                    Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
                    String username = claims.getSubject();
                    Object authoritiesClaims = claims.get("authorities");
                    // Se hace la conversion del objeto json string de los roles o authorities al tipo collection o list del tipo GrantedAuthority
                    Collection <? extends GrantedAuthority> authorities = Arrays.asList(
                        new ObjectMapper()
                        .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                        .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class)
                    );
                    // se crea un objeto o instancia del usuaro autenticado exitosamente
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    // se continua con los demas filtros de autenticacion que esten en el spring security config
                    chain.doFilter(request, response);
                } catch (JwtException e) {
                    // se envia un map tipo json indicando un mensaje de error en la validacion del token
                    Map<String, String> body = new HashMap<>();
                    body.put("error", e.getMessage());
                    body.put("message", "El token JWT es invalido!");

                    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(CONTENT_TYPE);
                }
    }


}
