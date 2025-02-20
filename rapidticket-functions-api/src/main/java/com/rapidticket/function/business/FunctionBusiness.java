package com.rapidticket.function.business;

import com.rapidticket.function.domain.dto.request.FunctionCreateRequestDTO;
import com.rapidticket.function.domain.dto.request.FunctionUpdateRequestDTO;
import com.rapidticket.function.domain.dto.response.FunctionListResponseDTO;
import com.rapidticket.function.domain.dto.request.FunctionListRequestDTO;
import com.rapidticket.function.domain.dto.FunctionDTO;
import com.rapidticket.function.response.Response;

import java.util.List;

public interface FunctionBusiness {
    Response<Void> updateWithCode(String code, FunctionUpdateRequestDTO dto);
    Response<List<FunctionListResponseDTO>> listAllWithFilters(FunctionListRequestDTO dto);
    Response<FunctionDTO> searchByCode(String code);
    Response<Void> deleteWithCode(String code);
    Response<Void> create(FunctionCreateRequestDTO dto);
}
