package com.rapidticket.show.repository;

import com.rapidticket.show.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
