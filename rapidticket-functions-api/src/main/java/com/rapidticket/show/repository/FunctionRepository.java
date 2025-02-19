package com.rapidticket.show.repository;

import com.rapidticket.show.domain.dto.request.FunctionListRequestDTO;
import com.rapidticket.show.model.Function;

import java.util.List;
import java.util.Optional;

public interface FunctionRepository {
    List<Function> findAllWithFilters(FunctionListRequestDTO functionListRequestDTO);
    boolean updateWithCode(String code, Function entity);
    Optional<Function> findByCode(String code);
    boolean existsByCode(String code);
    boolean delete(String code);
    String create(String showId, String venueId, Function entity);
}
