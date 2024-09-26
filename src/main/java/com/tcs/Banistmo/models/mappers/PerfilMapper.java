package com.tcs.Banistmo.models.mappers;

import com.tcs.Banistmo.models.dtos.PerfilDTO;
import com.tcs.Banistmo.models.entities.Perfil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {EmpleadoMapper.class})
public interface PerfilMapper {
    PerfilMapper INSTANCE = Mappers.getMapper(PerfilMapper.class);

    PerfilDTO PERFIL_DTO(Perfil perfil);
    Perfil PERFIL(PerfilDTO perfilDTO);
    List<PerfilDTO> PERFIL_DTO_LIST(List<Perfil> perfilList);
}
