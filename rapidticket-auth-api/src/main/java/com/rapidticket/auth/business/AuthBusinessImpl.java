package com.rapidticket.auth.business;

import static com.rapidticket.auth.utils.AuthConstantMessages.*;
import static com.rapidticket.auth.utils.ConstantMessages.*;

import com.rapidticket.auth.domain.dto.response.LoginResponseDTO;
import com.rapidticket.auth.domain.dto.request.LoginDTO;
import com.rapidticket.auth.validations.AuthValidation;
import com.rapidticket.auth.exception.CustomException;
import com.rapidticket.auth.repository.UserRepository;
import com.rapidticket.auth.utils.JwtTokenUtils;
import com.rapidticket.auth.response.Response;
import com.rapidticket.auth.utils.CryptoUtils;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import com.rapidticket.auth.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthBusinessImpl implements AuthBusiness {
    private final UserRepository userRepository;
    private final CryptoUtils cryptoUtils;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public Response<LoginResponseDTO> login(LoginDTO dto) {
        log.info("Starting login with email: {} ", dto.getEmail());
        Response<LoginResponseDTO> response = new Response<>();
        try {
            User user = this.userRepository.findByEmail(dto.getEmail()).orElse(null);
            AuthValidation.isNull(user == null);
            AuthValidation.isVerifyPassword(this.cryptoUtils.verifyPassword(dto.getPassword(), user.getPassword()));
            String token = this.jwtTokenUtils.generateToken(dto.getEmail(), List.of(user.getRole()));
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
