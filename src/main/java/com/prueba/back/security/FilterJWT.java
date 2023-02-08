package com.prueba.back.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FilterJWT extends OncePerRequestFilter {

    @Autowired
    private JWTSecurity jwtSecurity;
    @Autowired
    private UserDetailsService userDetailsService;

    //Proporciona un método doFilterInternal con argumentos HttpServletRequest y HttpServletResponse.
    //verifica si en el encabezado esta un token y lo valida
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //valida el name sea Authorization
        String authorizationHeader = request.getHeader("Authorization");

        //valida si comienza con la palabra Bearer y valida que el valor no sea null
        //toma el valor despues a partir del 7 espacio(6 de bearer y 1 del espacio en blanco)
        //toma el valor del username
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String jwt = authorizationHeader.substring(7);
            String username = jwtSecurity.extractUsername(jwt);

            //valida que aun no exista alguna autenticacion para este usuario
            //asignamos y tomamos los valores del user detail
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                /*valida el jwt. si pasa la validacion:
                *levantamos sesion para ese usuario con todas las autorizaciones
                * le enviamos al authToken los detalles de lo que esta recibiendo
                * Se asigna la autenticacion
                */
                if (jwtSecurity.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        //luego de pasar todas las validaciones, y filtro sea evaluado con filterchain
        filterChain.doFilter(request, response);
    }

    }

