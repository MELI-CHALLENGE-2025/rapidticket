package com.rapidticket.show.business;

import static com.rapidticket.show.utils.messages.ShowConstantMessages.*;
import static com.rapidticket.show.utils.messages.ConstantMessages.*;

import com.rapidticket.show.domain.dto.request.ShowListRequestDTO;
import com.rapidticket.show.domain.dto.request.ShowUpdateRequestDTO;
import com.rapidticket.show.validations.ShowValidation;
import com.rapidticket.show.repository.ShowRepository;
import com.rapidticket.show.exception.CustomException;
import com.rapidticket.show.domain.mapper.ShowMapper;
import com.rapidticket.show.domain.dto.ShowDTO;
import org.springframework.stereotype.Service;
import com.rapidticket.show.response.Response;
import org.springframework.http.HttpStatus;
import com.rapidticket.show.model.Show;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowBusinessImpl implements ShowBusiness {

    private final ShowRepository showRepository;

    @Override
    public Response<Void> create(ShowDTO dto) throws CustomException {
        log.info("Starting show creation process - Show name: {}", dto.getName());
        Response<Void> response = new Response<>();
        try {
            ShowValidation.isExistsByCode(this.showRepository.existsByCode(dto.getCode()));
            String showId = this.showRepository.create(ShowMapper.INSTANCE.toEntity(dto));
            ShowValidation.isCreate(showId != null);
            response = new Response<>(HttpStatus.CREATED.value(), UIM001, DIM001, EMPTY_STRING, EMPTY_STRING, null);
        } catch (CustomException e) {
            log.warn("Show creation validation failed - Error: {}, Status code: {}", e.getMessage(), e.getStatus());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Unexpected error during show creation - Error: {}", e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("Show creation completed - Name: {}, Status: {}, Code: {}",
                    dto.getName(),
                    response.getStatus(),
                    dto.getCode()
            );
        }

        return response;
    }

    @Override
    public Response<List<ShowDTO>> listAllWithFilters(ShowListRequestDTO showListRequestDTO) {
        log.info("Starting retrieval of all shows");
        Response<List<ShowDTO>> response = new Response<>();
        try {
            List<ShowDTO> listShowDto = ShowMapper.INSTANCE.toListDto(
                    this.showRepository.findAllWithFilters(
                            showListRequestDTO.getCode(),
                            showListRequestDTO.getName(),
                            showListRequestDTO.getSize(),
                            showListRequestDTO.getPage()
                    )
            );
            response = new Response<>(HttpStatus.OK.value(), UIM002, DIM002, EMPTY_STRING, EMPTY_STRING, listShowDto);
        } catch (Exception e) {
            log.error("Failed to retrieve show list - Error: {}", e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, new ArrayList<>());
        } finally {
            log.info("Show list retrieval completed - Total shows quantity: {}, Status: {}",
                    response.getData().size(),
                    response.getStatus()
            );
        }

        return response;
    }

    @Override
    public Response<ShowDTO> searchByCode(String code) {
        log.info("Starting show search - Code: {}", code);
        Response<ShowDTO> response = new Response<>();
        try {
            Show show = this.showRepository.findByCode(code).orElse(null);
            ShowValidation.isNull(show == null);
            response = new Response<>(HttpStatus.OK.value(), UIM003, DIM003, EMPTY_STRING, EMPTY_STRING, ShowMapper.INSTANCE.toDto(show));
        } catch (CustomException e) {
            log.warn("Show not found - Code: {}, Error: {}", code, e.getMessage());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Error searching for show - Code: {}, Error: {}", code, e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("Show search completed - Code: {}, Found: {}, Status: {}",
                    code,
                    response.getData() != null ? "Yes" : "No",
                    response.getStatus()
            );
        }

        return response;
    }

    @Override
    public Response<Void> updateWithCode(String code, ShowUpdateRequestDTO dto) {
        log.info("Starting show update - Code: {}, New name: {}", code, dto.getName());
        Response<Void> response = new Response<>();
        try {
            ShowValidation.isNotExistsByCode(this.showRepository.existsByCode(code));
            ShowValidation.isUpdate(this.showRepository.updateWithCode(code, ShowMapper.INSTANCE.toEntity(dto)));
            response = new Response<>(HttpStatus.OK.value(), UIM004, DIM004, EMPTY_STRING, EMPTY_STRING, null);
        } catch (CustomException e) {
            log.warn("Show update validation failed - Code: {}, Error: {}", code, e.getMessage());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Error updating show - Code: {}, Error: {}", code, e.getMessage(), e);
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), UEM000, DEM000, EMPTY_STRING, EMPTY_STRING, null);
        } finally {
            log.info("Show update completed - Code: {}, Status: {}, Result: {}",
                    code,
                    response.getStatus(),
                    response.getStatus() == HttpStatus.OK.value() ? "Success" : "Failed"
            );
        }

        return response;
    }

    @Override
    public Response<Void> deleteWithCode(String code) {
        log.info("Starting show deletion - Code: {}", code);
        Response<Void> response = new Response<>();
        try {
            ShowValidation.isNotExistsByCode(this.showRepository.existsByCode(code));
            ShowValidation.isDelete(this.showRepository.delete(code));
            response = new Response<>(HttpStatus.OK.value(), UIM005, DIM005, EMPTY_STRING, EMPTY_STRING, null);
        } catch (CustomException e) {
            log.warn("Show deletion validation failed - Code: {}, Error: {}", code, e.getMessage());
            response = new Response<>(e.getStatus(), e.getUserMessage(), e.getMessage(), EMPTY_STRING, EMPTY_STRING, null);
        } catch (Exception e) {
            log.error("Error deleting show - Code: {}, Error: {}", code, e.getMessage(), e);
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