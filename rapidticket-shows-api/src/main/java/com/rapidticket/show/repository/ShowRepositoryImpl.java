package com.rapidticket.show.repository;

import com.rapidticket.show.model.User;
import com.rapidticket.show.utils.enums.EnumRoleUser;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.rapidticket.show.model.Show;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ShowRepositoryImpl implements ShowRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String create(Show show, String userId) {
        try {
            UUID uuid = UUID.randomUUID();
            String sql = "INSERT INTO shows (id, code, name, description, created_by) VALUES (:id, :code, :name, :description, :createdBy)";
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", uuid)
                    .addValue("code", show.getCode())
                    .addValue("name", show.getName())
                    .addValue("description", show.getDescription())
                    .addValue("createdBy", UUID.fromString(userId));

            int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
            return rowsAffected > 0 ? uuid.toString() : null;
        } catch (Exception e) {
            log.error("Error inserting the show: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Show> findAllWithFilters(String code, String name, int page, int size) {
        try {
            int offset = (page - 1) * size;

            StringBuilder sql = new StringBuilder(
                    """
                    SELECT
                        s.id AS show_id, s.code AS show_code, s.name AS show_name, s.description AS show_description,
                        u.id AS user_id, u.email AS user_email, u.full_name AS user_full_name
                    FROM shows s
                    JOIN users u ON s.created_by = u.id
                    WHERE 1=1
                    """
            );

            MapSqlParameterSource params = new MapSqlParameterSource();

            if (code != null && !code.isEmpty()) {
                sql.append(" AND s.code LIKE :code");
                params.addValue("code", "%" + code + "%");
            }

            if (name != null && !name.isEmpty()) {
                sql.append(" AND LOWER(s.name) LIKE LOWER(:name)");
                params.addValue("name", "%" + name + "%");
            }

            sql.append(" ORDER BY s.created_at ASC LIMIT :size OFFSET :offset");
            params.addValue("size", size);
            params.addValue("offset", offset);

            return namedParameterJdbcTemplate.query(sql.toString(), params, mapper);
        } catch (Exception e) {
            log.error("Error retrieving shows with filters: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean existsByCode(String code) {
        try {
            String sql = "SELECT COUNT(*) FROM shows WHERE code = :code";
            MapSqlParameterSource params = new MapSqlParameterSource("code", code);
            Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count != null && count > 0;
        } catch (Exception e) {
            log.error("Error checking existence of show with code {}: {}", code, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Optional<Show> findByCode(String code) {
        try {
            String sql =
            """
            SELECT
                s.id AS show_id, s.code AS show_code, s.name AS show_name, s.description AS show_description,
                u.id AS user_id, u.email AS user_email, u.full_name AS user_full_name
            FROM shows s
            JOIN users u ON s.created_by = u.id
            WHERE s.code = :code
            """;
            MapSqlParameterSource params = new MapSqlParameterSource("code", code);
            List<Show> shows = namedParameterJdbcTemplate.query(sql, params, mapper);
            return shows.isEmpty() ? Optional.empty() : Optional.of(shows.get(0));
        } catch (Exception e) {
            log.error("Error retrieving show with code {}: {}", code, e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public boolean updateWithCode(String code, Show entity) {
        try {
            String sql = "UPDATE shows SET name = :name, description = :description WHERE code = :code";
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("name", entity.getName())
                    .addValue("description", entity.getDescription())
                    .addValue("code", code);

            int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
            return rowsAffected > 0;
        } catch (Exception e) {
            log.error("Error updating the show with code {}: {}", code, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String code) {
        try {
            String sql = "DELETE FROM shows WHERE code = :code";
            MapSqlParameterSource params = new MapSqlParameterSource("code", code);
            int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
            return rowsAffected > 0;
        } catch (Exception e) {
            log.error("Error deleting the show with code {}: {}", code, e.getMessage(), e);
            return false;
        }
    }

    private final RowMapper<Show> mapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("user_id"));
        user.setEmail(rs.getString("user_email"));
        user.setFullName(rs.getString("user_full_name"));

        Show show = new Show();
        show.setId(rs.getString("show_id"));
        show.setCode(rs.getString("show_code"));
        show.setName(rs.getString("show_name"));
        show.setDescription(rs.getString("show_description"));
        show.setCreatedBy(user);
        return show;
    };
}
