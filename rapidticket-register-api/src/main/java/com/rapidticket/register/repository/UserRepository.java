package com.rapidticket.register.repository;

import com.rapidticket.register.model.User;

public interface UserRepository {
    boolean existsByEmail(String code);
    String create(User user);
}
