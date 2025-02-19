package com.rapidticket.show.business;

import com.rapidticket.show.domain.dto.request.VenueListRequestDTO;
import com.rapidticket.show.domain.dto.request.VenueUpdateRequestDTO;
import com.rapidticket.show.domain.dto.VenueDTO;
import com.rapidticket.show.response.Response;

import java.util.List;

public interface VenueBusiness {
    Response<Void> updateWithCode(String code, VenueUpdateRequestDTO dto);
    Response<List<VenueDTO>> listAllWithFilters(VenueListRequestDTO dto);
    Response<VenueDTO> searchByCode(String code);
    Response<Void> deleteWithCode(String code);
    Response<Void> create(VenueDTO dto);
}
