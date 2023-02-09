package com.prueba.back.persistance.validator;

import com.prueba.back.persistance.entity.UserLogin;
import com.prueba.back.persistance.repository.UserRepository;
import com.prueba.back.service.EncryptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginValidatorImpl {
    @Autowired
    private EncryptServiceImpl encryptService;

    @Autowired
    private UserRepository userRepository;

    private String pass;

    public void validator(int id, UserLogin data)throws  ApiUnProcessableEntity{

        userRepository.findById(id).
                map(user -> {
                    this.pass=user.getPassword();
                    return  user ;
                });

        Boolean verifyAutentication = encryptService.verifyPassword(
                data.getPassword(),
                this.pass
        );


        if(!verifyAutentication){ this.message("Contraseña incorrecta");}

        if(data.getEmail().isEmpty()){
            this.message("Por favor ingrese correo electronico");
        }

        if(data.getPassword().isEmpty()){
            this.message("Por favor ingrese la contraseña");
        }


    }

    private void message(String message) throws ApiUnProcessableEntity{
        throw new ApiUnProcessableEntity(message);
    }
}
