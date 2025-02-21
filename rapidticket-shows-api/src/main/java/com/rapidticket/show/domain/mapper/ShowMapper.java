package com.rapidticket.show.domain.mapper;

import com.rapidticket.show.domain.dto.ShowDTO;
import com.rapidticket.show.domain.dto.request.ShowUpdateRequestDTO;
import com.rapidticket.show.model.Show;
import com.rapidticket.show.model.User;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper
public interface ShowMapper {
    ShowMapper INSTANCE = Mappers.getMapper(ShowMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", source = "user")
    Show toEntity(ShowDTO dto, User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    Show toEntity(ShowUpdateRequestDTO dto);


    ShowDTO toDto(Show entities);
    List<ShowDTO> toListDto(List<Show> entities);
}
