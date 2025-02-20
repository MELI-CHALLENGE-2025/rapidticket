package com.rapidticket.venue.repository;

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
    public String create(Venue venue) {
        try {
            UUID uuid = UUID.randomUUID();
            String sql = "INSERT INTO venues (id, code, name, capacity, location) " +
                    "VALUES (:id, :code, :name, :capacity, :location)";

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", uuid.toString())
                    .addValue("code", venue.getCode())
                    .addValue("name", venue.getName())
                    .addValue(CAPACITY, venue.getCapacity())
                    .addValue(LOCATION, venue.getLocation());

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

            StringBuilder sql = new StringBuilder("SELECT id, code, name, location, capacity, created_at FROM venues WHERE 1=1 ");
            MapSqlParameterSource params = new MapSqlParameterSource();

            if (code != null && !code.isEmpty()) {
                sql.append("AND code LIKE :code ");
                params.addValue("code", "%" + code + "%");
            }

            if (name != null && !name.isEmpty()) {
                sql.append("AND name LIKE :name ");
                params.addValue("name", "%" + name + "%");
            }

            if (location != null && !location.isEmpty()) {
                sql.append("AND location LIKE :location ");
                params.addValue(LOCATION, "%" + location + "%");
            }

            if (minCapacity != null) {
                sql.append("AND capacity >= :minCapacity ");
                params.addValue("minCapacity", minCapacity);
            }

            if (maxCapacity != null) {
                sql.append("AND capacity <= :maxCapacity ");
                params.addValue("maxCapacity", maxCapacity);
            }

            sql.append("ORDER BY created_at ASC LIMIT :size OFFSET :offset");
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
            String sql = "SELECT * FROM venues WHERE code = :code";
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
        Venue venue = new Venue();
        venue.setId(rs.getString("id"));
        venue.setCode(rs.getString("code"));
        venue.setName(rs.getString("name"));
        venue.setCapacity(rs.getInt(CAPACITY));
        venue.setLocation(rs.getString(LOCATION));
        return venue;
    };
}
