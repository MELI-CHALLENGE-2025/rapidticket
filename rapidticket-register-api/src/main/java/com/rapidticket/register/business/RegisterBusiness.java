package com.rapidticket.register.business;

import com.rapidticket.register.domain.dto.request.RegisterDTO;
import com.rapidticket.register.response.Response;

public interface RegisterBusiness {
    Response<Void> register(RegisterDTO dto);
}
