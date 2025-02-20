package com.rapidticket.show.business;

import com.rapidticket.show.domain.dto.request.FunctionCreateRequestDTO;
import com.rapidticket.show.domain.dto.request.FunctionUpdateRequestDTO;
import com.rapidticket.show.domain.dto.request.FunctionListRequestDTO;
import com.rapidticket.show.domain.dto.response.FunctionListResponseDTO;
import com.rapidticket.show.model.Show;
import com.rapidticket.show.model.Venue;
import com.rapidticket.show.repository.FunctionRepository;
import com.rapidticket.show.utils.enums.EnumCurrency;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.rapidticket.show.domain.dto.FunctionDTO;
import com.rapidticket.show.response.Response;
import org.springframework.http.HttpStatus;
import com.rapidticket.show.model.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.rapidticket.show.utils.ConstantMessages.DEM000;
import static com.rapidticket.show.utils.ConstantMessages.UEM000;
import static com.rapidticket.show.utils.FunctionConstantMessages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunctionBusinessImplTest {
    private static final String ID_FUNCTION = "11111111-1111-1111-1111-111111111111";
    private static final String CODE_SHOW = "MOCKED01";
    private static final String CODE_VENUE = "MOCKED01";
    private static final Date DATE = new Date();
    private static final double BASE_PRICE = 10.0;
    private static final String CURRENCY = "USD";
    private static final String NULL_POINT_EXCEPTION = "Simulated NPE";
    private static final String NAME_VENUE = "Test Venue";
    private static final String LOCATION_VENUE = "Test Location";
    private static final int CAPACITY_VENUE = 300;
    private static final String NAME_SHOW = "Test Show";
    private static final String DESCRIPTION_SHOW = "Test Description";

    @Mock
    private FunctionRepository venueRepository;

    @InjectMocks
    private FunctionBusinessImpl venueBusiness;

    private FunctionCreateRequestDTO functionCreateRequestDTO;
    private Function function;
    private Show show;
    private Venue venue;
    private List<Function> listFunction;
    private FunctionListRequestDTO functionListRequestDTO;
    private FunctionUpdateRequestDTO functionUpdateRequestDTO;

    @BeforeEach
    void setUp() {
        functionCreateRequestDTO = new FunctionCreateRequestDTO();
        functionCreateRequestDTO.setCode(CODE_VENUE);
        functionCreateRequestDTO.setShowId(CODE_SHOW);
        functionCreateRequestDTO.setVenueId(CODE_VENUE);
        functionCreateRequestDTO.setDate(DATE);
        functionCreateRequestDTO.setBasePrice(BASE_PRICE);
        functionCreateRequestDTO.setCurrency(EnumCurrency.USD);

        venue = new Venue();
        venue.setCode(CODE_VENUE);
        venue.setName(NAME_VENUE);
        venue.setLocation(LOCATION_VENUE);
        venue.setCapacity(CAPACITY_VENUE);

        show = new Show();
        show.setCode(CODE_SHOW);
        show.setName(NAME_SHOW);
        show.setDescription(DESCRIPTION_SHOW);


        function = new Function();
        function.setCode(CODE_VENUE);
        function.setCode(CODE_VENUE);
        function.setShow(show);
        function.setVenue(venue);
        function.setDate(DATE);
        function.setBasePrice(BASE_PRICE);
        function.setCurrency(EnumCurrency.USD);

        listFunction = new ArrayList<>();
        listFunction.add(function);

        functionListRequestDTO = new FunctionListRequestDTO();
        functionListRequestDTO.setCode(CODE_VENUE);
        functionListRequestDTO.setShowCode(CODE_SHOW);
        functionListRequestDTO.setVenueCode(CODE_VENUE);
        functionListRequestDTO.setMinBasePrice(1.0);
        functionListRequestDTO.setMaxBasePrice(400.0);
        functionListRequestDTO.setCurrency(CURRENCY);
        functionListRequestDTO.setPage(1);
        functionListRequestDTO.setSize(1);

        functionUpdateRequestDTO = new FunctionUpdateRequestDTO();
        functionUpdateRequestDTO.setVenueCode(CODE_VENUE);
        functionUpdateRequestDTO.setShowCode(CODE_SHOW);
        functionUpdateRequestDTO.setDate(DATE);
        functionUpdateRequestDTO.setBasePrice(BASE_PRICE);
        functionUpdateRequestDTO.setCurrency(CURRENCY);
    }

    @Test
    void createSuccess() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(false);
        when(this.venueRepository.create(anyString(), anyString(), any(Function.class))).thenReturn(ID_FUNCTION);

        Response<Void> response = this.venueBusiness.create(functionCreateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(UIM001, response.getUserMessage());
        assertEquals(DIM001, response.getDeveloperMessage());
    }

    @Test
    void createFailure_isExistsByCode() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(true);

        Response<Void> response = this.venueBusiness.create(functionCreateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM002, response.getUserMessage());
        assertEquals(DEM002, response.getDeveloperMessage());
    }

    @Test
    void createFailure_isNotCreate() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(false);
        when(this.venueRepository.create(anyString(), anyString(), any(Function.class))).thenReturn(null);

        Response<Void> response = this.venueBusiness.create(functionCreateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM004, response.getUserMessage());
        assertEquals(DEM004, response.getDeveloperMessage());
    }

    @Test
    void createFailure_GenericException() {
        when(this.venueRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.venueBusiness.create(functionCreateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }

    @Test
    void listSuccess() {
        when(this.venueRepository.findAllWithFilters(functionListRequestDTO)).thenReturn(listFunction);

        Response<List<FunctionListResponseDTO>> response = this.venueBusiness.listAllWithFilters(functionListRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM002, response.getUserMessage());
        assertEquals(DIM002, response.getDeveloperMessage());
    }

    @Test
    void listFailure_GenericException() {
        when(this.venueRepository.findAllWithFilters(functionListRequestDTO)).thenThrow(new NullPointerException("Simulated NPE"));

        Response<List<FunctionListResponseDTO>> response = this.venueBusiness.listAllWithFilters(functionListRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM001, response.getUserMessage());
        assertEquals(DEM001, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeSuccess() {
        when(this.venueRepository.findByCode(anyString())).thenReturn(Optional.of(function));

        Response<FunctionDTO> response = this.venueBusiness.searchByCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM003, response.getUserMessage());
        assertEquals(DIM003, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeFailure_isNull() {
        when(this.venueRepository.findByCode(anyString())).thenReturn(Optional.empty());

        Response<FunctionDTO> response = this.venueBusiness.searchByCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM001, response.getUserMessage());
        assertEquals(DEM001, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeFailure_GenericException() {
        when(this.venueRepository.findByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<FunctionDTO> response = this.venueBusiness.searchByCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }


    @Test
    void updateWithCodeSuccess() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(true);
        when(this.venueRepository.updateWithCode(anyString(), any(Function.class))).thenReturn(true);

        Response<Void> response = this.venueBusiness.updateWithCode(CODE_VENUE, functionUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM004, response.getUserMessage());
        assertEquals(DIM004, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_isNotExistsByCode() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(false);

        Response<Void> response = this.venueBusiness.updateWithCode(CODE_VENUE, functionUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM003, response.getUserMessage());
        assertEquals(DEM003, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_isUpdate() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(true);
        when(this.venueRepository.updateWithCode(anyString(), any(Function.class))).thenReturn(false);

        Response<Void> response = this.venueBusiness.updateWithCode(CODE_VENUE, functionUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM005, response.getUserMessage());
        assertEquals(DEM005, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_GenericException() {
        when(this.venueRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.venueBusiness.updateWithCode(CODE_VENUE, functionUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeSuccess() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(true);
        when(this.venueRepository.delete(anyString())).thenReturn(true);

        Response<Void> response = this.venueBusiness.deleteWithCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM005, response.getUserMessage());
        assertEquals(DIM005, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeFailure_isNotExistsByCode() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(false);

        Response<Void> response = this.venueBusiness.deleteWithCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM003, response.getUserMessage());
        assertEquals(DEM003, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeFailure_isNotDelete() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(true);
        when(this.venueRepository.delete(anyString())).thenReturn(false);

        Response<Void> response = this.venueBusiness.deleteWithCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM006, response.getUserMessage());
        assertEquals(DEM006, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeFailure_GenericException() {
        when(this.venueRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.venueBusiness.deleteWithCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }
}
