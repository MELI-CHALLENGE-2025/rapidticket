package com.rapidticket.show.repository;

import com.rapidticket.show.model.Venue;

import java.util.List;
import java.util.Optional;

public interface VenueRepository {
    Optional<Venue> findByCode(String code);
}
