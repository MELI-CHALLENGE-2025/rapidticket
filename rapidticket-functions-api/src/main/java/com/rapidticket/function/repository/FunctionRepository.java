package com.rapidticket.function.repository;

import com.rapidticket.function.domain.dto.request.FunctionListRequestDTO;
import com.rapidticket.function.model.Function;

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
