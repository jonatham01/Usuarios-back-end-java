package com.prueba.back.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/*
clase encargada de seguridad de la aplicación
inyectectamos la dependencia del userDetail y la dependencia de la validación del jwt
*/

@EnableWebSecurity
public class SecurityConfig  {

    @Autowired
    private UserDetailsService userDetailsServiceOk;

    @Autowired
    private  FilterJWT filterJWT;

    /*
    Es del tipo  AuthenticationManager
    Añadimos al spring security los valores del userDetail y asi sea obligatoria su autenticacion
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http)throws Exception {
	return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsServiceOk)
                .and()
                .build();
    }

    /**
    *Es del tipo SecurityFilterChain. Filtra las peticiones
     * Se excluyen algunas rutas a las cuales se puede acceder sin autenticación
     * una vez se autentique se crea una session
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(filterJWT, UsernamePasswordAuthenticationFilter.class);

        return  http.csrf().disable().authorizeRequests().antMatchers("/**/autenticated",
                        "/save",

                        "/**/swagger-ui.html",
                        "/v2/api-docs/**",
                        "/swagger.json",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**").permitAll()
                .anyRequest()
                .authenticated()
                .and() .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();

    }
    /*
    @Override public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs/**");
    web.ignoring().antMatchers("/swagger.json");
    web.ignoring().antMatchers("/swagger-ui.html");
    web.ignoring().antMatchers("/swagger-resources/**");
    web.ignoring().antMatchers("/webjars/**");
}
*/

}
