package com.prueba.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;
/*
Anotamos la clase con las anotaciones @Configuration y @EnableSwagger2.
Con esto configuramos swagger para el proyecto
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    /*
    importamos  springfox.documentation.service.ApiKey;
    Asignamos el valor del name, keyname y el passAs
     */
    private ApiKey apiKey(){return new ApiKey("JWT", "Authorization", "header");}

    private static final Set<String> DEFAULT_PRODUCES_CONSUMES = new HashSet<>(Arrays.asList("application/json"));

    //configuracion de swagger para exponer los controladores, teniendo en cuenta la autenticacion con jwt
    @Bean
    public Docket api() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("Authorization")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .description("JWT token")
                .required(true)
                .build();
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(ApiInfo.DEFAULT)
                .produces(DEFAULT_PRODUCES_CONSUMES)
                .consumes(DEFAULT_PRODUCES_CONSUMES)
                .select()
                .build()
                // Setting globalOperationParameters ensures that authentication header is applied to all APIs
                .globalOperationParameters(parameters);
    }
    /*
    @Bean
    ////exponga el package donde estan nuestros controllers
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Arrays.asList(apiKey()))
                .select().apis(RequestHandlerSelectors.basePackage("com.prueba.back.controller;"))
                .paths(PathSelectors.any())
                .build();

    }*/

}
