package com.prueba.back.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
@Component
public class JWTSecurity {

    //Se establece la llame de seguridad para la creacion y validación del token
    private static final String KEY= "Team_589*4p4";

    /**
     * Permite generar el JWT de la session
     * Recibe el usuario de la session y asigna un tiempo de expiracion de 10 horas
     *Por ultimo utiliza el hash para crear el jwt, utilizacion la llave de seguridad
     */
    public String generate(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername()) //recibe el usuario de la sesion
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000 *60 *60 *10))
                .signWith(SignatureAlgorithm.HS256,KEY).compact();
    }
    /**
     *Se recibe como parametro el token y el userDetails, y se comparan  si son iguales
     * se valida que el token no haya expirado
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        return userDetails.getUsername()
                .equals(extractUsername(token))
                && !isTokenExpired(token);
    }

    //extrae el username a partir del token que recibe
    //en el subject esta el usuario de la peticion
    public String extractUsername(String token) {
        return getClaims(token)
                .getSubject();
    }

    //Se valida si el token ya expiro
    //trae la fecha de vencimiento
    //si esta antes de la fecha actual expiro(true) sino no a expirado (false)
    public boolean isTokenExpired(String token) {
        return getClaims(token).
                getExpiration()
                .before(new Date());
    }

    //Los claims son los objetos dentro de jwt, del cual podemos extrar el username, y la fecha de expiracion
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(KEY) //recibe la llave de la firma y verifica que sea correcta
                .parseClaimsJws(token). //recibe el token
                getBody();//obtenemos el cuerpo de ese token

    }
}
