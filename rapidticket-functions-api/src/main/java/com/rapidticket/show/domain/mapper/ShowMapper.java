package com.rapidticket.show.domain.mapper;

import com.rapidticket.show.domain.dto.ShowDTO;
import com.rapidticket.show.model.Show;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper
public interface ShowMapper {
    ShowMapper INSTANCE = Mappers.getMapper(ShowMapper.class);

    ShowDTO toDto(Show entities);
}
