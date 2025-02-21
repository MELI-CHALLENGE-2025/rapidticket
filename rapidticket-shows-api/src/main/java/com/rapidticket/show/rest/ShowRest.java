package com.rapidticket.show.rest;

import com.rapidticket.show.business.ShowBusiness;
import com.rapidticket.show.domain.dto.ShowDTO;
import com.rapidticket.show.domain.dto.request.ShowListRequestDTO;
import com.rapidticket.show.domain.dto.request.ShowUpdateRequestDTO;
import com.rapidticket.show.response.Response;
import io.swagger.v3.oas.annotations.*;
import org.springframework.security.core.context.SecurityContextHolder;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Shows", description = "Operations related to shows management")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/shows", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShowRest {

    private final ShowBusiness showService;

    /**
     * Create a new show.
     *
     * @param showDTO The show data to be created.
     * @return No content if create is successful.
     */
    @Operation(summary = "Create a new show", description = "Saves a new show in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show created successfully",
                    content = @Content(schema = @Schema(implementation = ShowDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<Void> create(@Valid @RequestBody ShowDTO showDTO) {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.showService.create(showDTO, subject);
    }

    /**
     * Retrieve a list of all available shows.
     *
     * @return List of all shows.
     */
    @Operation(summary = "List all shows", description = "Retrieves all available shows from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of shows retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ShowDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public Response<List<ShowDTO>> listAllWithFilters(
            @Valid @ModelAttribute ShowListRequestDTO dto
    ) {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.showService.listAllWithFilters(dto, subject);
    }

    /**
     * Find a show by its unique code.
     *
     * @param code Unique identifier of the show.
     * @return The show details.
     */
    @Operation(summary = "Find a show by code", description = "Retrieves details of a specific show using its unique code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show found successfully",
                    content = @Content(schema = @Schema(implementation = ShowDTO.class))),
            @ApiResponse(responseCode = "404", description = "Show not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{code}")
    public Response<ShowDTO> searchByCode(
            @Parameter(description = "Unique code of the show", required = true)
            @PathVariable String code) {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.showService.searchByCode(code, subject);
    }

    /**
     * Update an existing show by its unique code.
     *
     * @param code Unique identifier of the show to update.
     * @param dto  Updated show data.
     * @return No content if update is successful.
     */
    @Operation(summary = "Update a show by code", description = "Modifies an existing show in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Show updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Show not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<Void> updateWithCode(
            @Parameter(description = "Unique code of the show", required = true)
            @PathVariable String code,
            @Valid @RequestBody ShowUpdateRequestDTO dto) {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.showService.updateWithCode(code, dto, subject);
    }

    /**
     * Delete a show by its unique code.
     *
     * @param code Unique identifier of the show to delete.
     * @return No content if deletion is successful.
     */
    @Operation(summary = "Delete a show by code", description = "Removes a show from the system using its unique code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Show deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Show not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{code}")
    public Response<Void> deleteWithCode(
            @Parameter(description = "Unique code of the show", required = true)
            @PathVariable String code) {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.showService.deleteWithCode(code, subject);
    }
}
