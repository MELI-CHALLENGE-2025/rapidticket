package com.rapidticket.function.repository;

import com.rapidticket.function.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
