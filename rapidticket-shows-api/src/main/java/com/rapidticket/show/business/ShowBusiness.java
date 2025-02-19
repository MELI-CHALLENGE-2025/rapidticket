package com.rapidticket.show.business;

import com.rapidticket.show.domain.dto.ShowDTO;
import com.rapidticket.show.domain.dto.request.ShowListRequestDTO;
import com.rapidticket.show.domain.dto.request.ShowUpdateRequestDTO;
import com.rapidticket.show.response.Response;

import java.util.List;

public interface ShowBusiness {
    Response<Void> updateWithCode(String code, ShowUpdateRequestDTO dto);
    Response<ShowDTO> searchByCode(String code);
    Response<Void> deleteWithCode(String code);
    Response<Void> create(ShowDTO dto);
    Response<List<ShowDTO>> listAllWithFilters(ShowListRequestDTO showListRequestDTO);
}
