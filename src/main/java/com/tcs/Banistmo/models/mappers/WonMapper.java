package com.tcs.Banistmo.models.mappers;

import com.tcs.Banistmo.models.dtos.WonDTO;
import com.tcs.Banistmo.models.entities.Won;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {WonMapper.class})
public interface WonMapper {
    WonMapper INSTANCE = Mappers.getMapper(WonMapper.class);

    WonDTO WON_DTO(Won won);

    Won WON(WonDTO wonDTO);

    List<WonDTO> WON_DTO_LIST(List<Won> wonList);
}
