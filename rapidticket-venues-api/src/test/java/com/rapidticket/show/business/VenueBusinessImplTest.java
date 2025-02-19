package com.rapidticket.show.business;

import com.rapidticket.show.domain.dto.request.VenueUpdateRequestDTO;
import com.rapidticket.show.domain.dto.request.VenueListRequestDTO;
import com.rapidticket.show.repository.VenueRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.rapidticket.show.domain.dto.VenueDTO;
import com.rapidticket.show.response.Response;
import org.springframework.http.HttpStatus;
import com.rapidticket.show.model.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rapidticket.show.utils.ConstantMessages.DEM000;
import static com.rapidticket.show.utils.ConstantMessages.UEM000;
import static com.rapidticket.show.utils.VenueConstantMessages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenueBusinessImplTest {
    private static final String ID_VENUE = "11111111-1111-1111-1111-111111111111";
    private static final String CODE_VENUE = "MOCKED01";
    private static final String NAME_VENUE = "Test Venue";
    private static final String LOCATION_VENUE = "Test Location";
    private static final int CAPACITY_VENUE = 300;
    private static final String NULL_POINT_EXCEPTION = "Simulated NPE";

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueBusinessImpl venueBusiness;

    private VenueDTO venueDTO;
    private Venue venue;
    private List<Venue> listVenue;
    private VenueListRequestDTO venueListRequestDTO;
    private VenueUpdateRequestDTO venueUpdateRequestDTO;
    @BeforeEach
    void setUp() {
        venueDTO = new VenueDTO();
        venueDTO.setCode(CODE_VENUE);
        venueDTO.setName(NAME_VENUE);
        venueDTO.setLocation(LOCATION_VENUE);
        venueDTO.setCapacity(CAPACITY_VENUE);

        venue = new Venue();
        venue.setCode(CODE_VENUE);
        venue.setName(NAME_VENUE);
        venue.setLocation(LOCATION_VENUE);
        venue.setCapacity(CAPACITY_VENUE);

        listVenue = new ArrayList<>();
        listVenue.add(venue);

        venueListRequestDTO = new VenueListRequestDTO();
        venueListRequestDTO.setCode(CODE_VENUE);
        venueListRequestDTO.setName(NAME_VENUE);
        venueListRequestDTO.setLocation(LOCATION_VENUE);
        venueListRequestDTO.setMinCapacity(1);
        venueListRequestDTO.setMaxCapacity(400);
        venueListRequestDTO.setPage(1);
        venueListRequestDTO.setSize(1);

        venueUpdateRequestDTO = new VenueUpdateRequestDTO();
        venueUpdateRequestDTO.setName(NAME_VENUE);
        venueUpdateRequestDTO.setLocation(LOCATION_VENUE);
        venueUpdateRequestDTO.setCapacity(300);
    }

    @Test
    void createSuccess() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(false);
        when(this.venueRepository.create(any(Venue.class))).thenReturn(ID_VENUE);

        Response<Void> response = this.venueBusiness.create(venueDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(UIM001, response.getUserMessage());
        assertEquals(DIM001, response.getDeveloperMessage());
    }

    @Test
    void createFailure_isExistsByCode() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(true);

        Response<Void> response = this.venueBusiness.create(venueDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM002, response.getUserMessage());
        assertEquals(DEM002, response.getDeveloperMessage());
    }

    @Test
    void createFailure_isNotCreate() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(false);
        when(this.venueRepository.create(any(Venue.class))).thenReturn(null);

        Response<Void> response = this.venueBusiness.create(venueDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM004, response.getUserMessage());
        assertEquals(DEM004, response.getDeveloperMessage());
    }

    @Test
    void createFailure_GenericException() {
        when(this.venueRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.venueBusiness.create(venueDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }

    @Test
    void listSuccess() {
        when(this.venueRepository.findAllWithFilters(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(listVenue);

        Response<List<VenueDTO>> response = this.venueBusiness.listAllWithFilters(venueListRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM002, response.getUserMessage());
        assertEquals(DIM002, response.getDeveloperMessage());
    }

    @Test
    void listFailure_GenericException() {
        when(this.venueRepository.findAllWithFilters(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt())).thenThrow(new NullPointerException("Simulated NPE"));

        Response<List<VenueDTO>> response = this.venueBusiness.listAllWithFilters(venueListRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM001, response.getUserMessage());
        assertEquals(DEM001, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeSuccess() {
        when(this.venueRepository.findByCode(anyString())).thenReturn(Optional.of(venue));

        Response<VenueDTO> response = this.venueBusiness.searchByCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM003, response.getUserMessage());
        assertEquals(DIM003, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeFailure_isNull() {
        when(this.venueRepository.findByCode(anyString())).thenReturn(Optional.empty());

        Response<VenueDTO> response = this.venueBusiness.searchByCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM001, response.getUserMessage());
        assertEquals(DEM001, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeFailure_GenericException() {
        when(this.venueRepository.findByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<VenueDTO> response = this.venueBusiness.searchByCode(CODE_VENUE);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }


    @Test
    void updateWithCodeSuccess() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(true);
        when(this.venueRepository.updateWithCode(anyString(), any(Venue.class))).thenReturn(true);

        Response<Void> response = this.venueBusiness.updateWithCode(CODE_VENUE, venueUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM004, response.getUserMessage());
        assertEquals(DIM004, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_isNotExistsByCode() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(false);

        Response<Void> response = this.venueBusiness.updateWithCode(CODE_VENUE, venueUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM003, response.getUserMessage());
        assertEquals(DEM003, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_isUpdate() {
        when(this.venueRepository.existsByCode(anyString())).thenReturn(true);
        when(this.venueRepository.updateWithCode(anyString(), any(Venue.class))).thenReturn(false);

        Response<Void> response = this.venueBusiness.updateWithCode(CODE_VENUE, venueUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM005, response.getUserMessage());
        assertEquals(DEM005, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_GenericException() {
        when(this.venueRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.venueBusiness.updateWithCode(CODE_VENUE, venueUpdateRequestDTO);

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
