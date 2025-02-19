package com.rapidticket.show.rest;

import com.rapidticket.show.domain.dto.request.FunctionUpdateRequestDTO;
import com.rapidticket.show.domain.dto.request.FunctionListRequestDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.rapidticket.show.business.FunctionBusiness;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;
import com.rapidticket.show.domain.dto.FunctionDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import com.rapidticket.show.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Functions", description = "Operations related to functions management")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/functions", produces = MediaType.APPLICATION_JSON_VALUE)
public class FunctionRest {
    private final FunctionBusiness functionBusiness;

    /**
     * Create a new function.
     *
     * @param dto The function data to be created.
     * @return No content if create is successful.
     */
    @Operation(summary = "Create a new function", description = "Saves a new function in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created successfully",
                    content = @Content(schema = @Schema(implementation = FunctionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<Void> create(@Valid @RequestBody FunctionDTO dto) {
        return this.functionBusiness.create(dto);
    }

    /**
     * Retrieve a list of all available functions.
     *
     * @return List of all functions.
     */
    @Operation(summary = "List all functions", description = "Retrieves all available functions from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of functions retrieved successfully",
                    content = @Content(schema = @Schema(implementation = FunctionDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public Response<List<FunctionDTO>> listAllWithFilters(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String showCode,
            @RequestParam(required = false) String venueCode,
            @RequestParam(required = false) Double minBasePrice,
            @RequestParam(required = false) Double maxBasePrice,
            @RequestParam(required = false) String currency,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        FunctionListRequestDTO dto = new FunctionListRequestDTO();
        dto.setCode(code);
        dto.setShowCode(showCode);
        dto.setVenueCode(venueCode);
        dto.setMinBasePrice(minBasePrice);
        dto.setMaxBasePrice(maxBasePrice);
        dto.setCurrency(currency);
        dto.setPage(page);
        dto.setSize(size);

        return this.functionBusiness.listAllWithFilters(dto);
    }

    /**
     * Find a function by its unique code.
     *
     * @param code Unique identifier of the function.
     * @return The function details.
     */
    @Operation(summary = "Find a function by code", description = "Retrieves details of a specific function using its unique code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Function found successfully",
                    content = @Content(schema = @Schema(implementation = FunctionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Function not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{code}")
    public Response<FunctionDTO> searchByCode(
            @Parameter(description = "Unique code of the function", required = true)
            @PathVariable String code) {
        return this.functionBusiness.searchByCode(code);
    }

    /**
     * Update an existing function by its unique code.
     *
     * @param code Unique identifier of the function to update.
     * @param dto Updated function data.
     * @return No content if update is successful.
     */
    @Operation(summary = "Update a function by code", description = "Modifies an existing function in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Function updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Function not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<Void> updateWithCode(
            @Parameter(description = "Unique code of the function", required = true)
            @PathVariable String code,
            @Valid @RequestBody FunctionUpdateRequestDTO dto) {
        return this.functionBusiness.updateWithCode(code, dto);
    }

    /**
     * Delete a function by its unique code.
     *
     * @param code Unique identifier of the function to delete.
     * @return No content if deletion is successful.
     */
    @Operation(summary = "Delete a function by code", description = "Removes a function from the system using its unique code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Function deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Function not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{code}")
    public Response<Void> deleteWithCode(
            @Parameter(description = "Unique code of the function", required = true)
            @PathVariable String code) {
        return this.functionBusiness.deleteWithCode(code);
    }
}
