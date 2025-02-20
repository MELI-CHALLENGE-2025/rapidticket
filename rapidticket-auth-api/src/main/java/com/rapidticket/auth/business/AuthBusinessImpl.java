package com.rapidticket.auth.business;

import static com.rapidticket.auth.utils.AuthConstantMessages.*;
import static com.rapidticket.auth.utils.ConstantMessages.*;

import com.rapidticket.auth.domain.dto.response.LoginResponseDTO;
import com.rapidticket.auth.domain.dto.request.RegisterDTO;
import com.rapidticket.auth.domain.dto.request.LoginDTO;
import com.rapidticket.auth.validations.AuthValidation;
import com.rapidticket.auth.exception.CustomException;
import com.rapidticket.auth.repository.UserRepository;
import com.rapidticket.auth.domain.mapper.AuthMapper;
import com.rapidticket.auth.utils.JwtTokenUtils;
import com.rapidticket.auth.response.Response;
import com.rapidticket.auth.utils.CryptoUtils;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import com.rapidticket.auth.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthBusinessImpl implements AuthBusiness {
    private final UserRepository userRepository;
    private final CryptoUtils cryptoUtils;

    @Override
    public Response<Void> register(RegisterDTO dto) {
        log.info("Starting register register process - User email: {}", dto.getEmail());
        Response<Void> response = new Response<>();
        try {
            AuthValidation.isExistsByEmail(this.userRepository.existsByEmail(dto.getEmail()));
            dto.setPassword(this.cryptoUtils.hashPassword(dto.getPassword()));
            String userId = this.userRepository.create(AuthMapper.INSTANCE.toEntity(dto));
            AuthValidation.isCreate(userId != null);
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

    @Override
    public Response<LoginResponseDTO> login(LoginDTO dto) {
        log.info("Starting login with email: {} ", dto.getEmail());
        Response<LoginResponseDTO> response = new Response<>();
        try {
            User user = this.userRepository.findByEmail(dto.getEmail()).orElse(null);
            AuthValidation.isNull(user == null);
            AuthValidation.isVerifyPassword(this.cryptoUtils.verifyPassword(dto.getPassword(), user.getPassword()));
            String token = JwtTokenUtils.generateToken(dto.getEmail());
            response = new Response<>(HttpStatus.OK.value(), UIM002, DIM002, EMPTY_STRING, EMPTY_STRING, new LoginResponseDTO(token));
        } catch (CustomException e) {
            log.warn("User login validation failed - Error: {}, Status code: {}", e.getMessage(), e.getStatus());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        }  catch (Exception e) {
            log.error("Failed user login - Error: {}", e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("User login completed - Total Users quantity: {}, Status: {}",
                    response.getData(),
                    response.getStatus()
            );
        }

        return response;
    }
}
