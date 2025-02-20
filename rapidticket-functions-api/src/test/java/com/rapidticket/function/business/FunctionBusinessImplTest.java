package com.rapidticket.function.business;

import com.rapidticket.function.domain.dto.request.FunctionCreateRequestDTO;
import com.rapidticket.function.domain.dto.request.FunctionUpdateRequestDTO;
import com.rapidticket.function.domain.dto.response.FunctionListResponseDTO;
import com.rapidticket.function.domain.dto.request.FunctionListRequestDTO;
import com.rapidticket.function.repository.FunctionRepository;
import com.rapidticket.function.repository.ShowRepository;
import com.rapidticket.function.repository.VenueRepository;
import com.rapidticket.function.utils.ConstantMessages;
import com.rapidticket.function.utils.FunctionConstantMessages;
import com.rapidticket.function.utils.ShowConstantMessages;
import com.rapidticket.function.utils.VenueConstantMessages;
import com.rapidticket.function.utils.enums.EnumCurrency;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.rapidticket.function.domain.dto.FunctionDTO;
import com.rapidticket.function.response.Response;
import org.springframework.http.HttpStatus;
import com.rapidticket.function.model.Function;
import org.junit.jupiter.api.BeforeEach;
import com.rapidticket.function.model.Venue;
import com.rapidticket.function.model.Show;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunctionBusinessImplTest {
    private static final String ID_FUNCTION = "11111111-1111-1111-1111-111111111111";
    private static final String ID_SHOW = "11111111-1111-1111-1111-111111111111";
    private static final String ID_VENUE = "11111111-1111-1111-1111-111111111111";
    private static final String CODE_SHOW = "MOCKED01";
    private static final String CODE_VENUE = "MOCKED01";
    private static final Timestamp DATE = new Timestamp(System.currentTimeMillis());
    private static final double BASE_PRICE = 10.0;
    private static final String CURRENCY = "USD";
    private static final String NULL_POINT_EXCEPTION = "Simulated NPE";
    private static final String NAME_VENUE = "Test Venue";
    private static final String LOCATION_VENUE = "Test Location";
    private static final int CAPACITY_VENUE = 300;
    private static final String NAME_SHOW = "Test Show";
    private static final String DESCRIPTION_SHOW = "Test Description";

    @Mock
    private FunctionRepository functionRepository;
    @Mock
    private ShowRepository showRepository;
    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private FunctionBusinessImpl functionBusinessImpl;

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
        functionCreateRequestDTO.setShowCode(CODE_SHOW);
        functionCreateRequestDTO.setVenueCode(CODE_VENUE);
        functionCreateRequestDTO.setDate(DATE);
        functionCreateRequestDTO.setBasePrice(BASE_PRICE);
        functionCreateRequestDTO.setCurrency(EnumCurrency.USD.name());

        venue = new Venue();
        venue.setId(ID_VENUE);
        venue.setCode(CODE_VENUE);
        venue.setName(NAME_VENUE);
        venue.setLocation(LOCATION_VENUE);
        venue.setCapacity(CAPACITY_VENUE);

        show = new Show();
        show.setId(ID_SHOW);
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
        when(this.functionRepository.existsByCode(anyString())).thenReturn(false);
        when(this.showRepository.findByCode(anyString())).thenReturn(Optional.of(show));
        when(this.venueRepository.findByCode(anyString())).thenReturn(Optional.of(venue));
        when(this.functionRepository.create(anyString(), anyString(), any(Function.class))).thenReturn(ID_FUNCTION);

        Response<Void> response = this.functionBusinessImpl.create(functionCreateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UIM001, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DIM001, response.getDeveloperMessage());
    }

    @Test
    void createFailure_isExistsByCode() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(true);

        Response<Void> response = this.functionBusinessImpl.create(functionCreateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UEM002, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DEM002, response.getDeveloperMessage());
    }

    @Test
    void createFailure_showIsNull() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(false);
        when(this.showRepository.findByCode(anyString())).thenReturn(Optional.empty());

        Response<Void> response = this.functionBusinessImpl.create(functionCreateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(ShowConstantMessages.UEM001, response.getUserMessage());
        assertEquals(ShowConstantMessages.DEM001, response.getDeveloperMessage());
    }

    @Test
    void createFailure_venueIsNull() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(false);
        when(this.showRepository.findByCode(anyString())).thenReturn(Optional.of(show));
        when(this.venueRepository.findByCode(anyString())).thenReturn(Optional.empty());

        Response<Void> response = this.functionBusinessImpl.create(functionCreateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(VenueConstantMessages.UEM001, response.getUserMessage());
        assertEquals(VenueConstantMessages.DEM001, response.getDeveloperMessage());
    }

    @Test
    void createFailure_isNotCreate() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(false);
        when(this.showRepository.findByCode(anyString())).thenReturn(Optional.of(show));
        when(this.venueRepository.findByCode(anyString())).thenReturn(Optional.of(venue));
        when(this.functionRepository.create(anyString(), anyString(), any(Function.class))).thenReturn(null);

        Response<Void> response = this.functionBusinessImpl.create(functionCreateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UEM004, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DEM004, response.getDeveloperMessage());
    }

    @Test
    void createFailure_GenericException() {
        when(this.functionRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.functionBusinessImpl.create(functionCreateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(ConstantMessages.UEM000, response.getUserMessage());
        assertEquals(ConstantMessages.DEM000, response.getDeveloperMessage());
    }

    @Test
    void findAllWithFiltersSuccess() {
        when(this.functionRepository.findAllWithFilters(functionListRequestDTO)).thenReturn(listFunction);

        Response<List<FunctionListResponseDTO>> response = this.functionBusinessImpl.listAllWithFilters(functionListRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UIM002, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DIM002, response.getDeveloperMessage());
    }

    @Test
    void listAllWithFiltersFailure_GenericException() {
        when(this.functionRepository.findAllWithFilters(functionListRequestDTO)).thenThrow(new NullPointerException("Simulated NPE"));

        Response<List<FunctionListResponseDTO>> response = this.functionBusinessImpl.listAllWithFilters(functionListRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(ConstantMessages.UEM000, response.getUserMessage());
        assertEquals(ConstantMessages.DEM000, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeSuccess() {
        when(this.functionRepository.findByCode(anyString())).thenReturn(Optional.of(function));

        Response<FunctionDTO> response = this.functionBusinessImpl.searchByCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UIM003, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DIM003, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeFailure_isNull() {
        when(this.functionRepository.findByCode(anyString())).thenReturn(Optional.empty());

        Response<FunctionDTO> response = this.functionBusinessImpl.searchByCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UEM001, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DEM001, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeFailure_GenericException() {
        when(this.functionRepository.findByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<FunctionDTO> response = this.functionBusinessImpl.searchByCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(ConstantMessages.UEM000, response.getUserMessage());
        assertEquals(ConstantMessages.DEM000, response.getDeveloperMessage());
    }


    @Test
    void updateWithCodeSuccess() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(true);
        when(this.showRepository.findByCode(anyString())).thenReturn(Optional.of(show));
        when(this.venueRepository.findByCode(anyString())).thenReturn(Optional.of(venue));
        when(this.functionRepository.updateWithCode(anyString(), any(Function.class))).thenReturn(true);

        Response<Void> response = this.functionBusinessImpl.updateWithCode(CODE_VENUE, functionUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UIM004, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DIM004, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_isNotExistsByCode() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(false);

        Response<Void> response = this.functionBusinessImpl.updateWithCode(CODE_VENUE, functionUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UEM003, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DEM003, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_showIsNull() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(true);
        when(this.showRepository.findByCode(anyString())).thenReturn(Optional.empty());

        Response<Void> response = this.functionBusinessImpl.updateWithCode(CODE_VENUE, functionUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(ShowConstantMessages.UEM001, response.getUserMessage());
        assertEquals(ShowConstantMessages.DEM001, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_venueIsnull() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(true);
        when(this.showRepository.findByCode(anyString())).thenReturn(Optional.of(show));
        when(this.venueRepository.findByCode(anyString())).thenReturn(Optional.empty());

        Response<Void> response = this.functionBusinessImpl.updateWithCode(CODE_VENUE, functionUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(VenueConstantMessages.UEM001, response.getUserMessage());
        assertEquals(VenueConstantMessages.DEM001, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_isUpdate() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(true);
        when(this.showRepository.findByCode(anyString())).thenReturn(Optional.of(show));
        when(this.venueRepository.findByCode(anyString())).thenReturn(Optional.of(venue));
        when(this.functionRepository.updateWithCode(anyString(), any(Function.class))).thenReturn(false);

        Response<Void> response = this.functionBusinessImpl.updateWithCode(CODE_VENUE, functionUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UEM005, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DEM005, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_GenericException() {
        when(this.functionRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.functionBusinessImpl.updateWithCode(CODE_VENUE, functionUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(ConstantMessages.UEM000, response.getUserMessage());
        assertEquals(ConstantMessages.DEM000, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeSuccess() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(true);
        when(this.functionRepository.delete(anyString())).thenReturn(true);

        Response<Void> response = this.functionBusinessImpl.deleteWithCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UIM005, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DIM005, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeFailure_isNotExistsByCode() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(false);

        Response<Void> response = this.functionBusinessImpl.deleteWithCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UEM003, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DEM003, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeFailure_isNotDelete() {
        when(this.functionRepository.existsByCode(anyString())).thenReturn(true);
        when(this.functionRepository.delete(anyString())).thenReturn(false);

        Response<Void> response = this.functionBusinessImpl.deleteWithCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(FunctionConstantMessages.UEM006, response.getUserMessage());
        assertEquals(FunctionConstantMessages.DEM006, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeFailure_GenericException() {
        when(this.functionRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.functionBusinessImpl.deleteWithCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(ConstantMessages.UEM000, response.getUserMessage());
        assertEquals(ConstantMessages.DEM000, response.getDeveloperMessage());
    }
}
