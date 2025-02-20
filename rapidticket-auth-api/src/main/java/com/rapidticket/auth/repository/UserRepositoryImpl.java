package com.rapidticket.auth.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import com.rapidticket.auth.utils.enums.EnumRoleUser;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import com.rapidticket.auth.model.User;
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

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = :email";
            SqlParameterSource params = new MapSqlParameterSource(EMAIL, email);
            List<User> users = namedParameterJdbcTemplate.query(sql, params, mapper);

            if (users.isEmpty()) {
                log.warn("Users with email {} not found", email);
                return Optional.empty();
            } else {
                return Optional.of(users.get(0));
            }
        } catch (Exception e) {
            log.error("Error retrieving user with email {}: {}", email, e.getMessage());
            return Optional.empty();
        }
    }

    private final RowMapper<User> mapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString(EMAIL));
        user.setPassword(rs.getString("password_hash"));
        user.setRole(EnumRoleUser.valueOf(rs.getString("role")));

        return user;
    };
}
