package com.rapidticket.venue.repository;

import com.rapidticket.venue.model.User;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.rapidticket.venue.model.Venue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class VenueRepositoryImpl implements VenueRepository {
    private static final String CAPACITY = "capacity";
    private static final String LOCATION = "location";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String create(Venue venue, String userId) {
        try {
            UUID uuid = UUID.randomUUID();
            String sql = "INSERT INTO venues (id, code, name, capacity, location, created_by) " +
                    "VALUES (:id, :code, :name, :capacity, :location, :createdBy)";

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", uuid)
                    .addValue("code", venue.getCode())
                    .addValue("name", venue.getName())
                    .addValue(CAPACITY, venue.getCapacity())
                    .addValue(LOCATION, venue.getLocation())
                    .addValue("createdBy", UUID.fromString(userId));


            int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
            return rowsAffected > 0 ? uuid.toString() : null;
        } catch (Exception e) {
            log.error("Error inserting the venue: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean existsByCode(String code) {
        try {
            String sql = "SELECT COUNT(*) FROM venues WHERE code = :code";
            SqlParameterSource params = new MapSqlParameterSource("code", code);
            Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count != null && count > 0;
        } catch (Exception e) {
            log.error("Error checking existence of venue with code {}: {}", code, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<Venue> findAllWithFilters(String code, String name, String location, Integer minCapacity, Integer maxCapacity, int page, int size) {
        try {
            int offset = (page - 1) * size;

            StringBuilder sql = new StringBuilder(
                    """
                    SELECT
                        v.id AS venue_id, v.code AS venue_code, v.name AS venue_name, v.capacity AS venue_capacity, v.location AS venue_location,
                        u.id AS user_id, u.email AS user_email, u.full_name AS user_full_name
                    FROM venues v
                    JOIN users u ON v.created_by = u.id
                    WHERE 1=1
                    """
            );

            MapSqlParameterSource params = new MapSqlParameterSource();

            if (code != null && !code.isEmpty()) {
                sql.append("AND v.code LIKE :code ");
                params.addValue("code", "%" + code + "%");
            }

            if (name != null && !name.isEmpty()) {
                sql.append("AND v.name LIKE :name ");
                params.addValue("name", "%" + name + "%");
            }

            if (location != null && !location.isEmpty()) {
                sql.append("AND v.location LIKE :location ");
                params.addValue("venue_location", "%" + location + "%");
            }

            if (minCapacity != null) {
                sql.append("AND v.capacity >= :minCapacity ");
                params.addValue("minCapacity", minCapacity);
            }

            if (maxCapacity != null) {
                sql.append("AND v.capacity <= :maxCapacity ");
                params.addValue("maxCapacity", maxCapacity);
            }

            sql.append("ORDER BY v.created_at ASC LIMIT :size OFFSET :offset");
            params.addValue("size", size);
            params.addValue("offset", offset);

            return namedParameterJdbcTemplate.query(sql.toString(), params, mapper);
        } catch (Exception e) {
            log.error("Error retrieving venues with filters: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Venue> findByCode(String code) {
        try {
            String sql =
                """
                SELECT
                    v.id AS venue_id, v.code AS venue_code, v.name AS venue_name, v.capacity AS venue_capacity, v.location AS venue_location,
                    u.id AS user_id, u.email AS user_email, u.full_name AS user_full_name
                FROM venues v
                JOIN users u ON v.created_by = u.id
                WHERE v.code = :code
                """;
            SqlParameterSource params = new MapSqlParameterSource("code", code);
            List<Venue> venues = namedParameterJdbcTemplate.query(sql, params, mapper);

            if (venues.isEmpty()) {
                log.warn("Venues with code {} not found", code);
                return Optional.empty();
            } else {
                return Optional.of(venues.get(0));
            }
        } catch (Exception e) {
            log.error("Error retrieving venue with code {}: {}", code, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean updateWithCode(String code, Venue entity) {
        try {
            String sql = "UPDATE venues SET name = :name, location = :location, capacity = :capacity WHERE code = :code";
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("name", entity.getName())
                    .addValue(LOCATION, entity.getLocation())
                    .addValue(CAPACITY, entity.getCapacity())
                    .addValue("code", code);

            int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
            return rowsAffected > 0;
        } catch (Exception e) {
            log.error("Error updating the venue with code {}: {}", code, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String code) {
        try {
            String sql = "DELETE FROM venues WHERE code = :code";
            SqlParameterSource params = new MapSqlParameterSource("code", code);
            int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
            return rowsAffected > 0;
        } catch (Exception e) {
            log.error("Error deleting the venue with code {}: {}", code, e.getMessage(), e);
            return false;
        }
    }

    private final RowMapper<Venue> mapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("user_id"));
        user.setEmail(rs.getString("user_email"));
        user.setFullName(rs.getString("user_full_name"));

        Venue venue = new Venue();
        venue.setId(rs.getString("venue_id"));
        venue.setCode(rs.getString("venue_code"));
        venue.setName(rs.getString("venue_name"));
        venue.setCapacity(rs.getInt("venue_capacity"));
        venue.setLocation(rs.getString("venue_location"));
        venue.setCreatedBy(user);

        return venue;
    };
}
