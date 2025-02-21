package com.rapidticket.register.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import com.rapidticket.register.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Types;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private static final String EMAIL = "email";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String create(User user) {
        try {
            UUID uuid = UUID.randomUUID();
            String sql = "INSERT INTO users (id, email, password_hash, full_name, role) " +
                    "VALUES (:id, :email, :password, :full_name, :role)";

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", uuid)
                    .addValue(EMAIL, user.getEmail())
                    .addValue("password", user.getPassword())
                    .addValue("full_name", user.getFullName())
                    .addValue("role", user.getRole().name(), Types.OTHER);

            int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
            return rowsAffected > 0 ? uuid.toString() : null;
        } catch (Exception e) {
            log.error("Error inserting the user: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            String sql = "SELECT COUNT(*) FROM users WHERE email = :email";
            SqlParameterSource params = new MapSqlParameterSource(EMAIL, email);
            Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count != null && count > 0;
        } catch (Exception e) {
            log.error("Error checking existence of user with email {}: {}", email, e.getMessage(), e);
            return false;
        }
    }
}
