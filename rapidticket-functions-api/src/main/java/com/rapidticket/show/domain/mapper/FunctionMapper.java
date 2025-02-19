package com.rapidticket.show.domain.mapper;

import com.rapidticket.show.domain.dto.FunctionDTO;
import com.rapidticket.show.domain.dto.request.FunctionUpdateRequestDTO;
import com.rapidticket.show.model.Function;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FunctionMapper {
    FunctionMapper INSTANCE = Mappers.getMapper(FunctionMapper.class);

    @Mapping(target = "id", ignore = true)
    Function toEntity(FunctionDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    Function toEntity(FunctionUpdateRequestDTO dto);

    List<FunctionDTO> toListDto(List<Function> entities);
    FunctionDTO toDto(Function entity);
}
