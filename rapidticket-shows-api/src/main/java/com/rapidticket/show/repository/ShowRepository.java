package com.rapidticket.show.repository;

import com.rapidticket.show.model.Show;
import java.util.Optional;
import java.util.List;

public interface ShowRepository {
    boolean updateWithCode(String code, Show entity);
    Optional<Show> findByCode(String code);
    boolean existsByCode(String code);
    boolean delete(String code);
    String create(Show entity, String userId);
    List<Show> findAllWithFilters(String code, String name, int page, int size);
}
