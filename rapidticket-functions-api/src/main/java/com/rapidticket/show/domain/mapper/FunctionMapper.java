package com.rapidticket.show.domain.mapper;

import com.rapidticket.show.domain.dto.FunctionDTO;
import com.rapidticket.show.domain.dto.ShowDTO;
import com.rapidticket.show.domain.dto.VenueDTO;
import com.rapidticket.show.domain.dto.request.FunctionCreateRequestDTO;
import com.rapidticket.show.domain.dto.request.FunctionUpdateRequestDTO;
import com.rapidticket.show.domain.dto.response.FunctionListResponseDTO;
import com.rapidticket.show.model.Function;
import com.rapidticket.show.model.Show;
import com.rapidticket.show.model.Venue;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FunctionMapper {
    FunctionMapper INSTANCE = Mappers.getMapper(FunctionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", source = "dto.code")
    @Mapping(target = "show", source = "show")
    @Mapping(target = "venue", source = "venue")
    Function toEntity(FunctionCreateRequestDTO dto, Show show, Venue venue);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "show", source = "show")
    @Mapping(target = "venue", source = "venue")
    Function toEntity(FunctionUpdateRequestDTO dto, Show show, Venue venue);

    @Mapping(target = "code", source = "entity.code")
    @Mapping(target = "show", source = "show")
    @Mapping(target = "venue", source = "venue")
    FunctionDTO toDto(Function entity, ShowDTO show, VenueDTO venue);

    FunctionListResponseDTO toFunctionListResponseDto(Function entity);

    List<FunctionListResponseDTO> toListDto(List<Function> entities);
}
