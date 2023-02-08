package com.prueba.back.persistance.validator;

import com.prueba.back.persistance.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validator(User request)throws  ApiUnProcessableEntity{

        if(request.getUserName()==null || request.getUserName().isEmpty()){
            this.message("El nombre es obligatorio");
        }
        if(request.getUserName().length()<3){
            this.message("El nombre es muy corto,debe tener minimo 3 caracteres");
        }
        if(request.getLastName()==null || request.getLastName().isEmpty()){
            this.message("El apellido es obligatorio");
        }
        if(request.getEmail()==null || request.getEmail().isEmpty()){
            this.message("El correo electronico es obligatorio");
        }
        if(request.getPassword()==null || request.getPassword().isEmpty()){
            this.message("La contraseña es obligatoria");
        }
    }

    private void message(String message) throws ApiUnProcessableEntity{
        throw new ApiUnProcessableEntity(message);
    }
}
