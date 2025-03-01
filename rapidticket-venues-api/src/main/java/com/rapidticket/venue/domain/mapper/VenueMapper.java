package com.rapidticket.venue.domain.mapper;

import com.rapidticket.venue.domain.dto.VenueDTO;
import com.rapidticket.venue.domain.dto.request.VenueUpdateRequestDTO;
import com.rapidticket.venue.model.Venue;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface VenueMapper {
    VenueMapper INSTANCE = Mappers.getMapper(VenueMapper.class);

    @Mapping(target = "id", ignore = true)
    Venue toEntity(VenueDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    Venue toEntity(VenueUpdateRequestDTO dto);

    List<VenueDTO> toListDto(List<Venue> entities);
    VenueDTO toDto(Venue entity);
}
