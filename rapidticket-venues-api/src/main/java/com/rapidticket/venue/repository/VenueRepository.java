package com.rapidticket.venue.repository;

import com.rapidticket.venue.model.Venue;

import java.util.List;
import java.util.Optional;

public interface VenueRepository {
    List<Venue> findAllWithFilters(String code, String name, String location, Integer minCapacity, Integer maxCapacity, int page, int size);
    boolean updateWithCode(String code, Venue entity);
    Optional<Venue> findByCode(String code);
    boolean existsByCode(String code);
    boolean delete(String code);
    String create(Venue entity, String userId);
}
