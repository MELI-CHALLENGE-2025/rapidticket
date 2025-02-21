package com.rapidticket.venue.business;

import com.rapidticket.venue.domain.dto.request.VenueListRequestDTO;
import com.rapidticket.venue.domain.dto.request.VenueUpdateRequestDTO;
import com.rapidticket.venue.domain.dto.VenueDTO;
import com.rapidticket.venue.response.Response;

import java.util.List;

public interface VenueBusiness {
    Response<Void> updateWithCode(String code, VenueUpdateRequestDTO dto, String subject);
    Response<List<VenueDTO>> listAllWithFilters(VenueListRequestDTO dto, String subject);
    Response<VenueDTO> searchByCode(String code, String subject);
    Response<Void> deleteWithCode(String code, String subject);
    Response<Void> create(VenueDTO dto, String subject);
}
