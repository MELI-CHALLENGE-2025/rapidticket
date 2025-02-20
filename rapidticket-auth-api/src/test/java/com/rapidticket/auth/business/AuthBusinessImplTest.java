package com.rapidticket.auth.business;

import com.rapidticket.auth.domain.dto.response.LoginResponseDTO;
import com.rapidticket.auth.domain.dto.request.LoginDTO;
import com.rapidticket.auth.repository.UserRepository;
import com.rapidticket.auth.utils.enums.EnumRoleUser;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.rapidticket.auth.response.Response;
import com.rapidticket.auth.utils.CryptoUtils;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import com.rapidticket.auth.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static com.rapidticket.auth.utils.ConstantMessages.DEM000;
import static com.rapidticket.auth.utils.ConstantMessages.UEM000;
import static com.rapidticket.auth.utils.AuthConstantMessages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthBusinessImplTest {
    private static final String ID_USER = "11111111-1111-1111-1111-111111111111";
    private static final String FULL_NAME = "full_name";
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "password";
    private static final String NULL_POINT_EXCEPTION = "Simulated NPE";

    @Mock
    private UserRepository userRepository;
    @Mock
    private CryptoUtils cryptoUtils;

    @InjectMocks
    private AuthBusinessImpl authBusiness;

    private LoginDTO loginDto;
    private User user;

    @BeforeEach
    void setUp() {
        loginDto = new LoginDTO();
        loginDto.setEmail(EMAIL);
        loginDto.setPassword(PASSWORD);

        user = new User();
        user.setId(ID_USER);
        user.setFullName(FULL_NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setRole(EnumRoleUser.ADMIN);
    }

    @Test
    void loginSuccess() {
        when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(this.cryptoUtils.verifyPassword(anyString(), anyString())).thenReturn(true);
        Response<LoginResponseDTO> response = this.authBusiness.login(loginDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM002, response.getUserMessage());
        assertEquals(DIM002, response.getDeveloperMessage());
    }

    @Test
    void loginFailure_isNull() {
        when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Response<LoginResponseDTO> response = this.authBusiness.login(loginDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM001, response.getUserMessage());
        assertEquals(DEM001, response.getDeveloperMessage());
    }

    @Test
    void loginFailure_isVerifyPassword() {
        when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(this.cryptoUtils.verifyPassword(anyString(), anyString())).thenReturn(false);

        Response<LoginResponseDTO> response = this.authBusiness.login(loginDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM005, response.getUserMessage());
        assertEquals(DEM005, response.getDeveloperMessage());
    }

    @Test
    void loginFailure_GenericException() {
        when(this.userRepository.findByEmail(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<LoginResponseDTO> response = this.authBusiness.login(loginDto);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }

}
