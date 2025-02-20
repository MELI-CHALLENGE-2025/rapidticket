package com.rapidticket.auth.business;

import com.rapidticket.auth.domain.dto.request.LoginDTO;
import com.rapidticket.auth.domain.dto.response.LoginResponseDTO;
import com.rapidticket.auth.response.Response;

public interface AuthBusiness {
    Response<LoginResponseDTO> login(LoginDTO dto);
}
