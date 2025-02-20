package com.rapidticket.show.domain.mapper;

import com.rapidticket.show.domain.dto.VenueDTO;
import com.rapidticket.show.model.Venue;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper
public interface VenueMapper {
    VenueMapper INSTANCE = Mappers.getMapper(VenueMapper.class);

    VenueDTO toDto(Venue entity);
}
