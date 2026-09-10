package com.taller.ms_usuarios.service;

import com.taller.ms_usuarios.common.exception.UsuarioNotFoundException;
import com.taller.ms_usuarios.dto.UsuarioRequestDTO;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.mapper.UsuarioMapper;
import com.taller.ms_usuarios.model.Usuario;
import com.taller.ms_usuarios.repository.UsuarioRepository;
import com.taller.ms_usuarios.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioValidator usuarioValidator;

    public List<UsuarioResponseDTO> findAll() {
        List<Usuario> lista = usuarioRepository.findAll();
        return usuarioMapper.toResponseDTOList(lista);
    }

    public UsuarioResponseDTO crearUser(UsuarioRequestDTO userDTO) {

        usuarioValidator.checkEmailDuplicate(userDTO.getEmail());

        Usuario usuario = usuarioMapper.toEntity(userDTO);
        usuario = usuarioRepository.save(usuario);//Guardar en la base de datos

        UsuarioResponseDTO usuarioResponse = usuarioMapper.toResponse(usuario);
        return usuarioResponse;
    }//END method

    public UsuarioResponseDTO findById(Integer id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(
                        () -> new UsuarioNotFoundException(id)
                );

        return usuarioMapper.toResponse(usuario);
    }

}//END class
