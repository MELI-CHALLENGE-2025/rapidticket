package com.rapidticket.auth.domain.mapper;

import com.rapidticket.auth.domain.dto.request.RegisterDTO;
import com.rapidticket.auth.model.User;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterDTO dto);

}
