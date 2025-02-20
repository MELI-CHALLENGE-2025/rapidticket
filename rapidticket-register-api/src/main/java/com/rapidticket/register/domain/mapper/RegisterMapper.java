package com.rapidticket.register.domain.mapper;

import com.rapidticket.register.domain.dto.request.RegisterDTO;
import com.rapidticket.register.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegisterMapper {
    RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterDTO dto);

}
