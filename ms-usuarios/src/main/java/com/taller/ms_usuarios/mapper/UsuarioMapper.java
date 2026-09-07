package com.taller.ms_usuarios.mapper;

import com.taller.ms_usuarios.dto.UsuarioRequestDTO;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.model.Usuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Convierte RequestDTO a entidad
    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    // Convierte entidad a ResponseDTO
    UsuarioResponseDTO toResponse(Usuario usuario);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void updateEntity(
            @MappingTarget Usuario usuario,
            UsuarioRequestDTO dto
    );

    List<UsuarioResponseDTO> toResponseDTOList(List<Usuario> usuarios);

}//END class
