package com.tcs.Bancolombia.models.mappers;

import com.tcs.Bancolombia.models.dtos.CalendarioDTO;
import com.tcs.Bancolombia.models.entities.Calendario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CalendarioMapper.class})
public interface CalendarioMapper {
    CalendarioMapper INSTANCE = Mappers.getMapper(CalendarioMapper.class);

    CalendarioDTO CALENDARIO_DTO(Calendario calendario);

    Calendario CALENDARIO(CalendarioDTO calendarioDTO);

    List<CalendarioDTO> CALENDARIO_DTO_LIST(List<Calendario> calendarioList);
}
