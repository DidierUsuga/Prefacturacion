package com.tcs.Banistmo.models.mappers;

import com.tcs.Banistmo.models.dtos.NovedadDTO;
import com.tcs.Banistmo.models.entities.NovedadBanistmo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NovedadMapper {
    NovedadMapper INSTANCE = Mappers.getMapper(NovedadMapper.class);

    @Mapping(source = "empleado.perfil", target = "perfil")
    @Mapping(source = "empleado.won", target = "won")
    NovedadDTO NOVEDAD_DTO(NovedadBanistmo novedad);

    NovedadBanistmo NOVEDAD(NovedadDTO novedadDTO);

    List<NovedadDTO> NOVEDAD_DTO_LIST(List<NovedadBanistmo> novedades);
}
