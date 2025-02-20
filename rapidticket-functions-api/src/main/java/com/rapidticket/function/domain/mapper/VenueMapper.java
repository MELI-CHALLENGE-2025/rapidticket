package com.rapidticket.function.domain.mapper;

import com.rapidticket.function.domain.dto.VenueDTO;
import com.rapidticket.function.model.Venue;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper
public interface VenueMapper {
    VenueMapper INSTANCE = Mappers.getMapper(VenueMapper.class);

    VenueDTO toDto(Venue entity);
}
