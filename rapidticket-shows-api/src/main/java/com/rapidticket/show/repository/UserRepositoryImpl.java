package com.rapidticket.show.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import com.rapidticket.show.utils.enums.EnumRoleUser;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import com.rapidticket.show.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private static final String EMAIL = "email";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
