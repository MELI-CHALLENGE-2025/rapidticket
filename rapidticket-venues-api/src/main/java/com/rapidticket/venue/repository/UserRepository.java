package com.rapidticket.venue.repository;

import com.rapidticket.venue.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
