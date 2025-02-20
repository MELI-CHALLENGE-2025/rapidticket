package com.rapidticket.register.business;

import static com.rapidticket.register.utils.RegisterConstantMessages.*;
import static com.rapidticket.register.utils.ConstantMessages.*;

import com.rapidticket.register.domain.dto.request.RegisterDTO;
import com.rapidticket.register.domain.mapper.RegisterMapper;
import com.rapidticket.register.validations.RegisterValidation;
import com.rapidticket.register.exception.CustomException;
import com.rapidticket.register.repository.UserRepository;
import com.rapidticket.register.response.Response;
import com.rapidticket.register.utils.CryptoUtils;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterBusinessImpl implements RegisterBusiness {
    private final UserRepository userRepository;
    private final CryptoUtils cryptoUtils;

    @Override
    public Response<Void> register(RegisterDTO dto) {
        log.info("Starting register register process - User email: {}", dto.getEmail());
        Response<Void> response = new Response<>();
        try {
            RegisterValidation.isExistsByEmail(this.userRepository.existsByEmail(dto.getEmail()));
            dto.setPassword(this.cryptoUtils.hashPassword(dto.getPassword()));
            String userId = this.userRepository.create(RegisterMapper.INSTANCE.toEntity(dto));
            RegisterValidation.isCreate(userId != null);
            response = new Response<>(HttpStatus.CREATED.value(), UIM001, DIM001, EMPTY_STRING, EMPTY_STRING, null);
        } catch (CustomException e) {
            log.warn("User register validation failed - Error: {}, Status code: {}", e.getMessage(), e.getStatus());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Unexpected error during User register - Error: {}", e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("User register completed - Name: {}, Status: {}, email: {}",
                    dto.getFullName(),
                    response.getStatus(),
                    dto.getEmail()
            );
        }

        return response;
    }
}
