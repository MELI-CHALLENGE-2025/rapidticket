package com.rapidticket.show.business;

import com.rapidticket.show.domain.dto.ShowDTO;
import com.rapidticket.show.domain.dto.request.ShowListRequestDTO;
import com.rapidticket.show.domain.dto.request.ShowUpdateRequestDTO;
import com.rapidticket.show.response.Response;

import java.util.List;

public interface ShowBusiness {
    Response<Void> updateWithCode(String code, ShowUpdateRequestDTO dto, String subject);
    Response<ShowDTO> searchByCode(String code, String subject);
    Response<Void> deleteWithCode(String code, String subject);
    Response<Void> create(ShowDTO dto, String subject);
    Response<List<ShowDTO>> listAllWithFilters(ShowListRequestDTO showListRequestDTO, String subject);
}
