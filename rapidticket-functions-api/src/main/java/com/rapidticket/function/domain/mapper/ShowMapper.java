package com.rapidticket.function.domain.mapper;

import com.rapidticket.function.domain.dto.ShowDTO;
import com.rapidticket.function.model.Show;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper
public interface ShowMapper {
    ShowMapper INSTANCE = Mappers.getMapper(ShowMapper.class);

    ShowDTO toDto(Show entities);
}
