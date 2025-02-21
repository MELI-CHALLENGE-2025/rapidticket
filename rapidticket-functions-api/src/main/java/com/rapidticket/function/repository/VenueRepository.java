package com.rapidticket.function.repository;

import com.rapidticket.function.model.Venue;

import java.util.Optional;

public interface VenueRepository {
    Optional<Venue> findByCode(String code);
}
