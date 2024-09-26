package com.tcs.Bancolombia.models.mappers;

import com.tcs.Bancolombia.models.dtos.TipoNovedadDTO;
import com.tcs.Bancolombia.models.entities.TipoNovedad;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {TipoNovedadMapper.class})
public interface TipoNovedadMapper {
    TipoNovedadMapper INSTANCE = Mappers.getMapper(TipoNovedadMapper.class);

    TipoNovedadDTO TIPO_NOVEDAD_DTO(TipoNovedad tipoNovedad);

    TipoNovedad TIPO_NOVEDAD(TipoNovedadDTO tipoNovedadDTO);

    List<TipoNovedadDTO> TIPO_NOVEDAD_DTO_LIST(List<TipoNovedad> tipoNovedadList);
}
