package com.rapidticket.venue.business;

import static com.rapidticket.venue.utils.messages.ConstantMessages.*;
import static com.rapidticket.venue.utils.messages.VenueConstantMessages.*;

import com.rapidticket.venue.domain.dto.request.VenueUpdateRequestDTO;
import com.rapidticket.venue.domain.dto.request.VenueListRequestDTO;
import com.rapidticket.venue.validations.VenueValidation;
import com.rapidticket.venue.repository.VenueRepository;
import com.rapidticket.venue.domain.mapper.VenueMapper;
import com.rapidticket.venue.exception.CustomException;
import com.rapidticket.venue.domain.dto.VenueDTO;
import com.rapidticket.venue.response.Response;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import com.rapidticket.venue.model.Venue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VenueBusinessImpl implements VenueBusiness {
    private final VenueRepository venueRepository;

    @Override
    public Response<Void> create(VenueDTO dto) throws CustomException {
        log.info("Starting venue creation process - Venue code: {}", dto.getCode());
        Response<Void> response = new Response<>();
        try {
            VenueValidation.isExistsByCode(this.venueRepository.existsByCode(dto.getCode()));
            String venueId = this.venueRepository.create(VenueMapper.INSTANCE.toEntity(dto));
            VenueValidation.isCreate(venueId != null);
            response = new Response<>(HttpStatus.CREATED.value(), UIM001, DIM001, EMPTY_STRING, EMPTY_STRING, null);
        } catch (CustomException e) {
            log.warn("Venue creation validation failed - Error: {}, Status code: {}", e.getMessage(), e.getStatus());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Unexpected error during venue creation - Error: {}", e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("Venue creation completed - Name: {}, Status: {}, Code: {}",
                    dto.getName(),
                    response.getStatus(),
                    dto.getCode()
            );
        }

        return response;
    }

    @Override
    public Response<List<VenueDTO>> listAllWithFilters(VenueListRequestDTO venueListRequestDTO) {
        log.info("Starting retrieval of all venues");
        Response<List<VenueDTO>> response = new Response<>();
        try {
            List<VenueDTO> listVenueDto = VenueMapper.INSTANCE.toListDto(
                    venueRepository.findAllWithFilters(
                            venueListRequestDTO.getCode(),
                            venueListRequestDTO.getName(),
                            venueListRequestDTO.getLocation(),
                            venueListRequestDTO.getMinCapacity(),
                            venueListRequestDTO.getMaxCapacity(),
                            venueListRequestDTO.getSize(),
                            venueListRequestDTO.getPage()
                    )
            );
            response = new Response<>(HttpStatus.OK.value(), UIM002, DIM002, EMPTY_STRING, EMPTY_STRING, listVenueDto);
        } catch (Exception e) {
            log.error("Failed to retrieve venue list - Error: {}", e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM001, DEM001, EMPTY_STRING, EMPTY_STRING, new ArrayList<>());
        } finally {
            log.info("Venue list retrieval completed - Total venues quantity: {}, Status: {}",
                    response.getData().size(),
                    response.getStatus()
            );
        }

        return response;
    }

    @Override
    public Response<VenueDTO> searchByCode(String code) {
        log.info("Starting venue search - Code: {}", code);
        Response<VenueDTO> response = new Response<>();
        try {
            Venue venue = this.venueRepository.findByCode(code).orElse(null);
            VenueValidation.isNull(venue == null);
            response = new Response<>(HttpStatus.OK.value(), UIM003, DIM003, EMPTY_STRING, EMPTY_STRING, VenueMapper.INSTANCE.toDto(venue));
        } catch (CustomException e) {
            log.warn("Venue not found - Code: {}, Error: {}", code, e.getMessage());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Error searching for venue - Code: {}, Error: {}", code, e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("Venue search completed - Code: {}, Found: {}, Status: {}",
                    code,
                    response.getData() != null ? "Yes" : "No",
                    response.getStatus()
            );
        }

        return response;
    }

    @Override
    public Response<Void> updateWithCode(String code, VenueUpdateRequestDTO dto) {
        log.info("Starting venue update - Code: {}, New name: {}", code, dto.getName());
        Response<Void> response = new Response<>();
        try {
            VenueValidation.isNotExistsByCode(this.venueRepository.existsByCode(code));
            VenueValidation.isUpdate(this.venueRepository.updateWithCode(code, VenueMapper.INSTANCE.toEntity(dto)));
            response = new Response<>(HttpStatus.OK.value(), UIM004, DIM004, EMPTY_STRING, EMPTY_STRING, null);
        } catch (CustomException e) {
            log.warn("Venue update validation failed - Code: {}, Error: {}", code, e.getMessage());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Error updating venue - Code: {}, Error: {}", code, e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("Venue update completed - Code: {}, Status: {}, Result: {}",
                    code,
                    response.getStatus(),
                    response.getStatus() == HttpStatus.OK.value() ? "Success" : "Failed"
            );
        }

        return response;
    }

    @Override
    public Response<Void> deleteWithCode(String code) {
        log.info("Starting venue deletion - Code: {}", code);
        Response<Void> response = new Response<>();
        try {
            VenueValidation.isNotExistsByCode(this.venueRepository.existsByCode(code));
            VenueValidation.isDelete(this.venueRepository.delete(code));
            response = new Response<>(HttpStatus.OK.value(), UIM005, DIM005, EMPTY_STRING, EMPTY_STRING, null);
        } catch (CustomException e) {
            log.warn("Show deletion validation failed - Code: {}, Error: {}", code, e.getMessage());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Error deleting venue - Code: {}, Error: {}", code, e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("Show deletion completed - Code: {}, Status: {}, Result: {}",
                    code,
                    response.getStatus(),
                    response.getStatus() == HttpStatus.OK.value() ? "Success" : "Failed"
            );
        }

        return response;
    }
}
