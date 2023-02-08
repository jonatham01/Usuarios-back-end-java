package com.prueba.back.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
/*
utilizamos la Interfaz central que carga datos específicos del usuario (UserDetailsService)
sobreescribimos su metodo loadUserByUsername y retornamos el valor del username y password de la autenticación de spring security. Del tipo UserDetails
la contraseña no ha pasado por codificador, se añade noop
 */
@Service
public class UserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username,"{noop}12345",new ArrayList<>());
    }


}
