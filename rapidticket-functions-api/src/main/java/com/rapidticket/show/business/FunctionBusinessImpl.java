package com.rapidticket.show.business;

import static com.rapidticket.show.utils.ConstantMessages.*;
import static com.rapidticket.show.utils.FunctionConstantMessages.*;

import com.rapidticket.show.domain.dto.request.FunctionUpdateRequestDTO;
import com.rapidticket.show.domain.dto.request.FunctionListRequestDTO;
import com.rapidticket.show.model.Show;
import com.rapidticket.show.model.Venue;
import com.rapidticket.show.repository.ShowRepository;
import com.rapidticket.show.repository.VenueRepository;
import com.rapidticket.show.validations.FunctionValidation;
import com.rapidticket.show.repository.FunctionRepository;
import com.rapidticket.show.domain.mapper.FunctionMapper;
import com.rapidticket.show.exception.CustomException;
import com.rapidticket.show.domain.dto.FunctionDTO;
import com.rapidticket.show.response.Response;
import com.rapidticket.show.validations.ShowValidation;
import com.rapidticket.show.validations.VenueValidation;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import com.rapidticket.show.model.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FunctionBusinessImpl implements FunctionBusiness {
    private final FunctionRepository functionRepository;
    private final ShowRepository showRepository;
    private final VenueRepository venueRepository;

    @Override
    public Response<Void> create(FunctionDTO dto) throws CustomException {
        log.info("Starting function creation process - Function code: {}", dto.getCode());
        Response<Void> response = new Response<>();
        try {
            FunctionValidation.isExistsByCode(this.functionRepository.existsByCode(dto.getCode()));
            Show show = this.showRepository.findByCode(dto.getShowCode()).orElse(null);
            ShowValidation.isNull(show == null);
            Venue venue = this.venueRepository.findByCode(dto.getVenueCode()).orElse(null);
            VenueValidation.isNull(venue == null);
            String functionId = this.functionRepository.create(show.getId(), venue.getId(), FunctionMapper.INSTANCE.toEntity(dto));
            FunctionValidation.isCreate(functionId != null);
            response = new Response<>(HttpStatus.CREATED.value(), UIM001, DIM001, EMPTY_STRING, EMPTY_STRING, null);
        } catch (CustomException e) {
            log.warn("Function creation validation failed - Error: {}, Status code: {}", e.getMessage(), e.getStatus());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Unexpected error during function creation - Error: {}", e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("Function creation completed - Code: {}, Status: {}, Code: {}",
                    dto.getCode(),
                    response.getStatus(),
                    dto.getCode()
            );
        }

        return response;
    }

    @Override
    public Response<List<FunctionDTO>> listAllWithFilters(FunctionListRequestDTO functionListRequestDTO) {
        log.info("Starting retrieval of all functions");
        Response<List<FunctionDTO>> response = new Response<>();
        try {
            List<FunctionDTO> listFunctionDto = FunctionMapper.INSTANCE.toListDto(
                    functionRepository.findAllWithFilters(functionListRequestDTO)
            );
            response = new Response<>(HttpStatus.OK.value(), UIM002, DIM002, EMPTY_STRING, EMPTY_STRING, listFunctionDto);
        } catch (Exception e) {
            log.error("Failed to retrieve function list - Error: {}", e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM001, DEM001, EMPTY_STRING, EMPTY_STRING, new ArrayList<>());
        } finally {
            log.info("Function list retrieval completed - Total functions quantity: {}, Status: {}",
                    response.getData().size(),
                    response.getStatus()
            );
        }

        return response;
    }

    @Override
    public Response<FunctionDTO> searchByCode(String code) {
        log.info("Starting function search - Code: {}", code);
        Response<FunctionDTO> response = new Response<>();
        try {
            Function function = this.functionRepository.findByCode(code).orElse(null);
            FunctionValidation.isNull(function == null);
            response = new Response<>(HttpStatus.OK.value(), UIM003, DIM003, EMPTY_STRING, EMPTY_STRING, FunctionMapper.INSTANCE.toDto(function));
        } catch (CustomException e) {
            log.warn("Function not found - Code: {}, Error: {}", code, e.getMessage());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Error searching for function - Code: {}, Error: {}", code, e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("Function search completed - Code: {}, Found: {}, Status: {}",
                    code,
                    response.getData() != null ? "Yes" : "No",
                    response.getStatus()
            );
        }

        return response;
    }

    @Override
    public Response<Void> updateWithCode(String code, FunctionUpdateRequestDTO dto) {
        log.info("Starting function update - Code: {} ", code);
        Response<Void> response = new Response<>();
        try {
            FunctionValidation.isNotExistsByCode(this.functionRepository.existsByCode(code));
            FunctionValidation.isUpdate(this.functionRepository.updateWithCode(code, FunctionMapper.INSTANCE.toEntity(dto)));
            response = new Response<>(HttpStatus.OK.value(), UIM004, DIM004, EMPTY_STRING, EMPTY_STRING, null);
        } catch (CustomException e) {
            log.warn("Function update validation failed - Code: {}, Error: {}", code, e.getMessage());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Error updating function - Code: {}, Error: {}", code, e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("Function update completed - Code: {}, Status: {}, Result: {}",
                    code,
                    response.getStatus(),
                    response.getStatus() == HttpStatus.OK.value() ? "Success" : "Failed"
            );
        }

        return response;
    }

    @Override
    public Response<Void> deleteWithCode(String code) {
        log.info("Starting function deletion - Code: {}", code);
        Response<Void> response = new Response<>();
        try {
            FunctionValidation.isNotExistsByCode(this.functionRepository.existsByCode(code));
            FunctionValidation.isDelete(this.functionRepository.delete(code));
            response = new Response<>(HttpStatus.OK.value(), UIM005, DIM005, EMPTY_STRING, EMPTY_STRING, null);
        } catch (CustomException e) {
            log.warn("Show deletion validation failed - Code: {}, Error: {}", code, e.getMessage());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Error deleting function - Code: {}, Error: {}", code, e.getMessage(), e);
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
