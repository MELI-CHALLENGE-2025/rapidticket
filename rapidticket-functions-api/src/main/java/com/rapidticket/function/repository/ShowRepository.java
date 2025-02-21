package com.rapidticket.function.repository;

import com.rapidticket.function.model.Show;

import java.util.Optional;

public interface ShowRepository {
    Optional<Show> findByCode(String code);
}
