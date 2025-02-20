package com.rapidticket.function.repository;

import com.rapidticket.function.model.Venue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class VenueRepositoryImpl implements VenueRepository {
    private static final String CAPACITY = "capacity";
    private static final String LOCATION = "location";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
