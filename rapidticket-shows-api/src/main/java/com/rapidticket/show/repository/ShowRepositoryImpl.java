package com.rapidticket.show.repository;

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
    private static final String DESCRIPTION = "description";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String create(Show show) {
        try {
            UUID uuid = UUID.randomUUID();
            String sql = "INSERT INTO shows (id, code, name, description) VALUES (:id, :code, :name, :description)";
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", uuid.toString())
                    .addValue("code", show.getCode())
                    .addValue("name", show.getName())
                    .addValue(DESCRIPTION, show.getDescription());

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

            String sql = "SELECT id, code, name, description, created_at FROM shows WHERE 1=1";
            MapSqlParameterSource params = new MapSqlParameterSource();

            if (code != null && !code.isEmpty()) {
                sql += " AND code LIKE :code";
                params.addValue("code", "%" + code + "%");
            }

            if (name != null && !name.isEmpty()) {
                sql += " AND LOWER(name) LIKE LOWER(:name)";
                params.addValue("name", "%" + name + "%");
            }

            sql += " ORDER BY created_at ASC LIMIT :size OFFSET :offset";
            params.addValue("size", size);
            params.addValue("offset", offset);

            return namedParameterJdbcTemplate.query(sql, params, mapper);
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
            String sql = "SELECT * FROM shows WHERE code = :code";
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
                    .addValue(DESCRIPTION, entity.getDescription())
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
        Show show = new Show();
        show.setId(rs.getString("id"));
        show.setCode(rs.getString("code"));
        show.setName(rs.getString("name"));
        show.setDescription(rs.getString(DESCRIPTION));
        return show;
    };
}
