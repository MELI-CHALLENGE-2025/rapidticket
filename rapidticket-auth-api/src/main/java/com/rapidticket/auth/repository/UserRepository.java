package com.rapidticket.auth.repository;

import com.rapidticket.auth.model.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(String code);
    String create(User user);
    Optional<User> findByEmail(String email);
}
