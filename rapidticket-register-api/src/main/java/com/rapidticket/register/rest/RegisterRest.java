package com.rapidticket.register.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.rapidticket.register.domain.dto.request.RegisterDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import com.rapidticket.register.business.RegisterBusiness;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import com.rapidticket.register.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

@Tag(name = "Register", description = "Operations related to register management")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegisterRest {
    private final RegisterBusiness registerBusiness;

    /**
     * Register a new user.
     *
     * @param dto The user registration data.
     * @return No content if registration is successful.
     */
    @Operation(summary = "Register a new user", description = "Creates a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<Void> register(@Valid @RequestBody RegisterDTO dto) {
        return registerBusiness.register(dto);
    }
}
