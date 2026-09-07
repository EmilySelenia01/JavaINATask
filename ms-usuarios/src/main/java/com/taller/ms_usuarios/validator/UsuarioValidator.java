package com.taller.ms_usuarios.validator;

import com.taller.ms_usuarios.common.exception.EmailDuplicateException;
import com.taller.ms_usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

/*
bussines rules
es un objeto de componentes
checkea en el repositorio
 */
@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    //es para aplicar las reglas de negocio
    //aqui se aplica las excepciones personalizadas

    private final UsuarioRepository usuarioRepository;


    public void checkEmailDuplicate(String email) {
        // Consultar el repositorio para comprobar si existe

        if (usuarioRepository.findByEmail(email).isPresent())
            throw new EmailDuplicateException(email);
    }

}

