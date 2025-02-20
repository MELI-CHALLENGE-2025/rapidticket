package com.rapidticket.auth.rest;

import com.rapidticket.auth.domain.dto.response.LoginResponseDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.rapidticket.auth.domain.dto.request.LoginDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.rapidticket.auth.business.AuthBusiness;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import com.rapidticket.auth.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "Operations related to authentication management")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthRest {
    private final AuthBusiness authBusiness;

    /**
     * Login a user and generate a JWT token.
     *
     * @param dto The login credentials.
     * @return JWT token if login is successful.
     */
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<LoginResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        return authBusiness.login(dto);
    }

}
