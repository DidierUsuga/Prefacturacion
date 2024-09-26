package com.tcs.Banistmo.models.mappers;

import com.tcs.Banistmo.models.dtos.EmpleadoDTO;
import com.tcs.Banistmo.models.entities.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {PerfilMapper.class})
public interface EmpleadoMapper {
    EmpleadoMapper INSTANCE = Mappers.getMapper(EmpleadoMapper.class);

    EmpleadoDTO EMPLEADO_DTO(Empleado empleado);

    Empleado EMPLEADO(EmpleadoDTO empleadoDTO);

    List<EmpleadoDTO> EMPLEADO_DTO_LIST(List<Empleado> empleadoList);
}
