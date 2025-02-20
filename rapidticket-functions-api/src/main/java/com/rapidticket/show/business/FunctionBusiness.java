package com.rapidticket.show.business;

import com.rapidticket.show.domain.dto.request.FunctionCreateRequestDTO;
import com.rapidticket.show.domain.dto.request.FunctionUpdateRequestDTO;
import com.rapidticket.show.domain.dto.response.FunctionListResponseDTO;
import com.rapidticket.show.domain.dto.request.FunctionListRequestDTO;
import com.rapidticket.show.domain.dto.FunctionDTO;
import com.rapidticket.show.response.Response;

import java.util.List;

public interface FunctionBusiness {
    Response<Void> updateWithCode(String code, FunctionUpdateRequestDTO dto);
    Response<List<FunctionListResponseDTO>> listAllWithFilters(FunctionListRequestDTO dto);
    Response<FunctionDTO> searchByCode(String code);
    Response<Void> deleteWithCode(String code);
    Response<Void> create(FunctionCreateRequestDTO dto);
}
