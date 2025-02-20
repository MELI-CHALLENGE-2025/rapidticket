package com.rapidticket.register.business;

import com.rapidticket.register.domain.dto.request.RegisterDTO;
import com.rapidticket.register.repository.UserRepository;
import com.rapidticket.register.utils.enums.EnumRoleUser;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.rapidticket.register.response.Response;
import com.rapidticket.register.utils.CryptoUtils;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import com.rapidticket.register.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static com.rapidticket.register.utils.ConstantMessages.DEM000;
import static com.rapidticket.register.utils.ConstantMessages.UEM000;
import static com.rapidticket.register.utils.RegisterConstantMessages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterBusinessImplTest {
    private static final String ID_USER = "11111111-1111-1111-1111-111111111111";
    private static final String FULL_NAME = "full_name";
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_HASH = "password_hash";
    private static final String NULL_POINT_EXCEPTION = "Simulated NPE";

    @Mock
    private UserRepository userRepository;
    @Mock
    private CryptoUtils cryptoUtils;

    @InjectMocks
    private RegisterBusinessImpl registerBusiness;

    private RegisterDTO registerDto;

    @BeforeEach
    void setUp() {
        registerDto = new RegisterDTO();
        registerDto.setFullName(FULL_NAME);
        registerDto.setEmail(EMAIL);
        registerDto.setPassword(PASSWORD);
        registerDto.setRole(EnumRoleUser.ADMIN.name());
    }

    @Test
    void registerSuccess() {
        when(this.userRepository.existsByEmail(anyString())).thenReturn(false);
        when(this.cryptoUtils.hashPassword(anyString())).thenReturn(PASSWORD_HASH);
        when(this.userRepository.create(any(User.class))).thenReturn(ID_USER);

        Response<Void> response = this.registerBusiness.register(registerDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(UIM001, response.getUserMessage());
        assertEquals(DIM001, response.getDeveloperMessage());
    }

    @Test
    void registerFailure_existsByEmail() {
        when(this.userRepository.existsByEmail(anyString())).thenReturn(true);

        Response<Void> response = this.registerBusiness.register(registerDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM002, response.getUserMessage());
        assertEquals(DEM002, response.getDeveloperMessage());
    }

    @Test
    void registerFailure_isCreate() {
        when(this.userRepository.existsByEmail(anyString())).thenReturn(false);
        when(this.cryptoUtils.hashPassword(anyString())).thenReturn(PASSWORD_HASH);
        when(this.userRepository.create(any(User.class))).thenReturn(null);

        Response<Void> response = this.registerBusiness.register(registerDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM004, response.getUserMessage());
        assertEquals(DEM004, response.getDeveloperMessage());
    }

    @Test
    void registerFailure_GenericException() {
        when(this.userRepository.existsByEmail(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.registerBusiness.register(registerDto);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }

}
