package com.rapidticket.show.repository;

import com.rapidticket.show.model.Show;

import java.util.List;
import java.util.Optional;

public interface ShowRepository {
    Optional<Show> findByCode(String code);
}
