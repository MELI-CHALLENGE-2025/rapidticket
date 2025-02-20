package com.rapidticket.venue.rest;

import com.rapidticket.venue.domain.dto.request.VenueUpdateRequestDTO;
import com.rapidticket.venue.domain.dto.request.VenueListRequestDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.rapidticket.venue.business.VenueBusiness;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;
import com.rapidticket.venue.domain.dto.VenueDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import com.rapidticket.venue.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Venues", description = "Operations related to venues management")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/venues", produces = MediaType.APPLICATION_JSON_VALUE)
public class VenueRest {
    private final VenueBusiness venueBusiness;

    /**
     * Create a new venue.
     *
     * @param dto The venue data to be created.
     * @return No content if create is successful.
     */
    @Operation(summary = "Create a new venue", description = "Saves a new venue in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created successfully",
                    content = @Content(schema = @Schema(implementation = VenueDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<Void> create(@Valid @RequestBody VenueDTO dto) {
        return this.venueBusiness.create(dto);
    }

    /**
     * Retrieve a list of all available venues.
     *
     * @return List of all venues.
     */
    @Operation(summary = "List all venues", description = "Retrieves all available venues from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of venues retrieved successfully",
                    content = @Content(schema = @Schema(implementation = VenueDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public Response<List<VenueDTO>> listAllWithFilters(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        VenueListRequestDTO dto = new VenueListRequestDTO();
        dto.setName(name);
        dto.setCode(code);
        dto.setMinCapacity(minCapacity);
        dto.setMaxCapacity(maxCapacity);
        dto.setPage(page);
        dto.setSize(size);

        return this.venueBusiness.listAllWithFilters(dto);
    }

    /**
     * Find a venue by its unique code.
     *
     * @param code Unique identifier of the venue.
     * @return The venue details.
     */
    @Operation(summary = "Find a venue by code", description = "Retrieves details of a specific venue using its unique code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venue found successfully",
                    content = @Content(schema = @Schema(implementation = VenueDTO.class))),
            @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{code}")
    public Response<VenueDTO> searchByCode(
            @Parameter(description = "Unique code of the venue", required = true)
            @PathVariable String code) {
        return this.venueBusiness.searchByCode(code);
    }

    /**
     * Update an existing venue by its unique code.
     *
     * @param code Unique identifier of the venue to update.
     * @param dto Updated venue data.
     * @return No content if update is successful.
     */
    @Operation(summary = "Update a venue by code", description = "Modifies an existing venue in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venue updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<Void> updateWithCode(
            @Parameter(description = "Unique code of the venue", required = true)
            @PathVariable String code,
            @Valid @RequestBody VenueUpdateRequestDTO dto) {
        return this.venueBusiness.updateWithCode(code, dto);
    }

    /**
     * Delete a venue by its unique code.
     *
     * @param code Unique identifier of the venue to delete.
     * @return No content if deletion is successful.
     */
    @Operation(summary = "Delete a venue by code", description = "Removes a venue from the system using its unique code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{code}")
    public Response<Void> deleteWithCode(
            @Parameter(description = "Unique code of the venue", required = true)
            @PathVariable String code) {
        return this.venueBusiness.deleteWithCode(code);
    }
}
