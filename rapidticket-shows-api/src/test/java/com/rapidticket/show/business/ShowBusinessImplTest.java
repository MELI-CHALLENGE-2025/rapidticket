package com.rapidticket.show.business;

import com.rapidticket.show.ShowApplication;
import com.rapidticket.show.domain.dto.ShowDTO;
import com.rapidticket.show.domain.dto.request.ShowListRequestDTO;
import com.rapidticket.show.domain.dto.request.ShowUpdateRequestDTO;
import com.rapidticket.show.model.Show;
import com.rapidticket.show.repository.ShowRepository;
import com.rapidticket.show.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rapidticket.show.utils.messages.ConstantMessages.DEM000;
import static com.rapidticket.show.utils.messages.ConstantMessages.UEM000;
import static com.rapidticket.show.utils.messages.ShowConstantMessages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ShowApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class ShowBusinessImplTest {
    private static final String ID_VENUE = "11111111-1111-1111-1111-111111111111";
    private static final String CODE_SHOW = "MOCKED01";
    private static final String NAME_SHOW = "Test Show";
    private static final String DESCRIPTION_SHOW = "Test Description";
    private static final String NULL_POINT_EXCEPTION = "Simulated NPE";
    @Mock
    private ShowRepository showRepository;

    @InjectMocks
    private ShowBusinessImpl showBusiness;

    private ShowDTO showDTO;
    private Show show;
    private List<Show> listShow;
    private ShowListRequestDTO showListRequestDTO;
    private ShowUpdateRequestDTO showUpdateRequestDTO;

    @BeforeEach
    void setUp() {
        showDTO = new ShowDTO();
        showDTO.setCode(CODE_SHOW);
        showDTO.setName(NAME_SHOW);
        showDTO.setDescription(DESCRIPTION_SHOW);

        show = new Show();
        show.setCode(CODE_SHOW);
        show.setName(NAME_SHOW);
        show.setDescription(DESCRIPTION_SHOW);

        listShow = new ArrayList<>();
        listShow.add(show);

        showListRequestDTO = new ShowListRequestDTO();
        showListRequestDTO.setCode(CODE_SHOW);
        showListRequestDTO.setName(NAME_SHOW);
        showListRequestDTO.setPage(1);
        showListRequestDTO.setSize(1);

        showUpdateRequestDTO = new ShowUpdateRequestDTO();
        showUpdateRequestDTO.setName(NAME_SHOW);
        showUpdateRequestDTO.setDescription(DESCRIPTION_SHOW);
    }

    @Test
    void createSuccess() {
        when(this.showRepository.existsByCode(anyString())).thenReturn(false);
        when(this.showRepository.create(any(Show.class))).thenReturn(ID_VENUE);

        Response<Void> response = this.showBusiness.create(showDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(UIM001, response.getUserMessage());
        assertEquals(DIM001, response.getDeveloperMessage());
    }

    @Test
    void createFailure_isExistsByCode() {
        when(this.showRepository.existsByCode(anyString())).thenReturn(true);

        Response<Void> response = this.showBusiness.create(showDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM002, response.getUserMessage());
        assertEquals(DEM002, response.getDeveloperMessage());
    }

    @Test
    void createFailure_isNotCreate() {
        when(this.showRepository.existsByCode(anyString())).thenReturn(false);
        when(this.showRepository.create(any(Show.class))).thenReturn(null);

        Response<Void> response = this.showBusiness.create(showDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM004, response.getUserMessage());
        assertEquals(DEM004, response.getDeveloperMessage());
    }

    @Test
    void createFailure_GenericException() {
        when(this.showRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.showBusiness.create(showDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }

    @Test
    void listSuccess() {
        when(this.showRepository.findAllWithFilters(anyString(), anyString(), anyInt(), anyInt())).thenReturn(listShow);

        Response<List<ShowDTO>> response = this.showBusiness.listAllWithFilters(showListRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM002, response.getUserMessage());
        assertEquals(DIM002, response.getDeveloperMessage());
    }

    @Test
    void listFailure_GenericException() {
        when(this.showRepository.findAllWithFilters(anyString(), anyString(), anyInt(), anyInt())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<List<ShowDTO>> response = this.showBusiness.listAllWithFilters(showListRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeSuccess() {
        when(this.showRepository.findByCode(anyString())).thenReturn(Optional.of(show));

        Response<ShowDTO> response = this.showBusiness.searchByCode(CODE_SHOW);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM003, response.getUserMessage());
        assertEquals(DIM003, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeFailure_isNull() {
        when(this.showRepository.findByCode(anyString())).thenReturn(Optional.empty());

        Response<ShowDTO> response = this.showBusiness.searchByCode(CODE_SHOW);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM001, response.getUserMessage());
        assertEquals(DEM001, response.getDeveloperMessage());
    }

    @Test
    void searchByCodeFailure_GenericException() {
        when(this.showRepository.findByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<ShowDTO> response = this.showBusiness.searchByCode(CODE_SHOW);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }


    @Test
    void updateWithCodeSuccess() {
        when(this.showRepository.existsByCode(anyString())).thenReturn(true);
        when(this.showRepository.updateWithCode(anyString(), any(Show.class))).thenReturn(true);

        Response<Void> response = this.showBusiness.updateWithCode(CODE_SHOW, showUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM004, response.getUserMessage());
        assertEquals(DIM004, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_isNotExistsByCode() {
        when(this.showRepository.existsByCode(anyString())).thenReturn(false);

        Response<Void> response = this.showBusiness.updateWithCode(CODE_SHOW, showUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM003, response.getUserMessage());
        assertEquals(DEM003, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_isUpdate() {
        when(this.showRepository.existsByCode(anyString())).thenReturn(true);
        when(this.showRepository.updateWithCode(anyString(), any(Show.class))).thenReturn(false);

        Response<Void> response = this.showBusiness.updateWithCode(CODE_SHOW, showUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM005, response.getUserMessage());
        assertEquals(DEM005, response.getDeveloperMessage());
    }

    @Test
    void updateWithCodeFailure_GenericException() {
        when(this.showRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.showBusiness.updateWithCode(CODE_SHOW, showUpdateRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeSuccess() {
        when(this.showRepository.existsByCode(anyString())).thenReturn(true);
        when(this.showRepository.delete(anyString())).thenReturn(true);

        Response<Void> response = this.showBusiness.deleteWithCode(CODE_SHOW);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(UIM005, response.getUserMessage());
        assertEquals(DIM005, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeFailure_isNotExistsByCode() {
        when(this.showRepository.existsByCode(anyString())).thenReturn(false);

        Response<Void> response = this.showBusiness.deleteWithCode(CODE_SHOW);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM003, response.getUserMessage());
        assertEquals(DEM003, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeFailure_isDelete() {
        when(this.showRepository.existsByCode(anyString())).thenReturn(true);
        when(this.showRepository.delete(anyString())).thenReturn(false);

        Response<Void> response = this.showBusiness.deleteWithCode(CODE_SHOW);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(UEM006, response.getUserMessage());
        assertEquals(DEM006, response.getDeveloperMessage());
    }

    @Test
    void deleteWithCodeFailure_GenericException() {
        when(this.showRepository.existsByCode(anyString())).thenThrow(new NullPointerException(NULL_POINT_EXCEPTION));

        Response<Void> response = this.showBusiness.deleteWithCode(CODE_SHOW);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(UEM000, response.getUserMessage());
        assertEquals(DEM000, response.getDeveloperMessage());
    }
}
