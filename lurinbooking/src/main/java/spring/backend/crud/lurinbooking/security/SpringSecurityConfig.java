package spring.backend.crud.lurinbooking.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import spring.backend.crud.lurinbooking.security.filter.JwtAuthenticationFilter;
import spring.backend.crud.lurinbooking.security.filter.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Metodo para validar y autorizar los request
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(authz -> authz
        //los endpoints que estan con funcion permitAll son publicos, el resto de endpoinst estan protegidos y solo se acceden con token
        .requestMatchers(HttpMethod.GET,"/api/usuarios").permitAll()
        .requestMatchers(HttpMethod.POST,"/api/usuarios/registrar").permitAll()
        .requestMatchers(HttpMethod.POST,"/api/usuarios/").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/api/reservas/{reservaId}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.GET,"/api/reservas/usuario/{usuarioId}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.POST,"/api/reservas/buscarReservasXCriterios").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.GET,"/api/reservas/calculaTotal/{reservaId}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.POST,"/api/reservas/").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.GET,"/api/ambientes/").permitAll()
        .requestMatchers(HttpMethod.GET,"/api/usuarios/profile").permitAll()
        .requestMatchers(HttpMethod.PUT,"/api/reservas/{reservaId}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.DELETE,"/api/reservas/{reservaId}").hasRole("ADMIN")
        .requestMatchers(HttpMethod.POST,"/api/ambientes/").hasRole("ADMIN")
        .anyRequest().authenticated())
        // se añaden los filtros de autenticacion y validacion
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtValidationFilter(authenticationManager()))
        .csrf(config->config.disable())
        // se añade el filtro cors definido
        .cors(cors->cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
    }
     // configuracion del cors
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*")); // acepta las solicitudes de cualquier direccion, aqui se debe especificar el dominio o la direccion del frontend
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    //Filtro para el cors 
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    } 
}
