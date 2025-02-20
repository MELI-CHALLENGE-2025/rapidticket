package com.rapidticket.function.repository;

import com.rapidticket.function.domain.dto.request.FunctionListRequestDTO;
import com.rapidticket.function.model.Show;
import com.rapidticket.function.model.Venue;
import com.rapidticket.function.utils.enums.EnumCurrency;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.rapidticket.function.model.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Types;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FunctionRepositoryImpl implements FunctionRepository {
    private static final String SHOW_DESCRIPTION = "show_description";
    private static final String VENUE_CAPACITY = "venue_capacity";
    private static final String VENUE_LOCATION = "venue_location";
    private static final String SHOW_ID = "show_id";
    private static final String VENUE_ID = "venue_id";
    private static final String FUNCTION_BASE_PRICE = "function_base_price";
    private static final String FUNCTION_CURRENCY = "function_currency";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String create(String showId, String venueId, Function function) {
        try {
            UUID uuid = UUID.randomUUID();
            String sql = "INSERT INTO functions (id, code, show_id, venue_id, date, base_price, currency) " +
                    "VALUES (:id, :code, :show_id, :venue_id, :date, :base_price, :currency)";

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", uuid)
                    .addValue("code", function.getCode())
                    .addValue(SHOW_ID, UUID.fromString(showId))
                    .addValue(VENUE_ID, UUID.fromString(venueId))
                    .addValue("date", function.getDate())
                    .addValue(FUNCTION_BASE_PRICE, function.getBasePrice())
                    .addValue(FUNCTION_CURRENCY, function.getCurrency().name(), Types.OTHER);

            int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
            return rowsAffected > 0 ? uuid.toString() : null;
        } catch (Exception e) {
            log.error("Error inserting the function: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean existsByCode(String code) {
        try {
            String sql = "SELECT COUNT(*) FROM functions WHERE code = :code";
            SqlParameterSource params = new MapSqlParameterSource("code", code);
            Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count != null && count > 0;
        } catch (Exception e) {
            log.error("Error checking existence of function with code {}: {}", code, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<Function> findAllWithFilters(FunctionListRequestDTO dto) {
        try {
            int offset = (dto.getPage() - 1) * dto.getSize();

            StringBuilder sql = new StringBuilder(
                    """
                    SELECT
                        f.id AS function_id, f.code AS function_code, f.date AS function_date,
                        f.base_price AS function_base_price, f.currency AS function_currency, f.created_at, 
                        s.id AS show_id, s.code AS show_code, s.name AS show_name, s.description AS show_description,
                        v.id AS venue_id, v.code AS venue_code, v.name AS venue_name, v.capacity AS venue_capacity, v.location AS venue_location 
                    FROM functions f
                    JOIN shows s ON f.show_id = s.id
                    JOIN venues v ON f.venue_id = v.id
                    WHERE 1=1
                    """
            );
            MapSqlParameterSource params = new MapSqlParameterSource();

            if (dto.getCode() != null && !dto.getCode().isEmpty()) {
                sql.append("AND f.code LIKE :code ");
                params.addValue("code", "%" + dto.getCode() + "%");
            }

            if (dto.getShowCode() != null && !dto.getShowCode().isEmpty()) {
                sql.append("AND s.code LIKE :show_code ");
                params.addValue("show_code", "%" + dto.getShowCode() + "%");
            }

            if (dto.getVenueCode() != null && !dto.getVenueCode().isEmpty()) {
                sql.append("AND v.code LIKE :venue_code ");
                params.addValue("venue_code", "%" + dto.getVenueCode() + "%");
            }

            if (dto.getMinBasePrice() != null) {
                sql.append("AND f.base_price >= :minBasePrice ");
                params.addValue("minBasePrice", dto.getMinBasePrice());
            }

            if (dto.getMaxBasePrice() != null) {
                sql.append("AND f.base_price <= :maxBasePrice ");
                params.addValue("maxBasePrice", dto.getMaxBasePrice());
            }

            if (dto.getCurrency() != null) {
                sql.append("AND f.currency = :currencies ");
                params.addValue("currencies", dto.getCurrency());
            }

            sql.append("ORDER BY f.created_at ASC LIMIT :size OFFSET :offset");
            params.addValue("size", dto.getSize());
            params.addValue("offset", offset);

            return namedParameterJdbcTemplate.query(sql.toString(), params, mapper);
        } catch (Exception e) {
            log.error("Error retrieving functions with filters: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Function> findByCode(String code) {
        try {
            String sql =
            """
            SELECT
                f.id AS function_id, f.code AS function_code, f.date AS function_date, f.base_price AS function_base_price, f.currency AS function_currency,
                s.id AS show_id, s.code AS show_code, s.name AS show_name, s.description AS show_description,
                v.id AS venue_id, v.code AS venue_code, v.name AS venue_name, v.capacity AS venue_capacity, v.location AS venue_location
            FROM functions f
            JOIN shows s ON f.show_id = s.id
            JOIN venues v ON f.venue_id = v.id
            WHERE f.code = :code
            """;

            SqlParameterSource params = new MapSqlParameterSource("code", code);
            List<Function> functions = namedParameterJdbcTemplate.query(sql, params, mapper);

            if (functions.isEmpty()) {
                log.warn("Functions with code {} not found", code);
                return Optional.empty();
            } else {
                return Optional.of(functions.get(0));
            }
        } catch (Exception e) {
            log.error("Error retrieving function with code {}: {}", code, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean updateWithCode(String code, Function entity) {
        try {
            String sql = "UPDATE functions SET show_id = :show_id, venue_id = :venue_id, date = :date, base_price = :base_price, currency = :currency WHERE code = :code";
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue(SHOW_ID, UUID.fromString(entity.getShow().getId()))
                    .addValue(VENUE_ID, UUID.fromString(entity.getVenue().getId()))
                    .addValue("date", entity.getDate())
                    .addValue("base_price", entity.getBasePrice())
                    .addValue("currency", entity.getCurrency().name(), Types.OTHER)
                    .addValue("code", code);

            int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
            return rowsAffected > 0;
        } catch (Exception e) {
            log.error("Error updating the function with code {}: {}", code, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String code) {
        try {
            String sql = "DELETE FROM functions WHERE code = :code";
            SqlParameterSource params = new MapSqlParameterSource("code", code);
            int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
            return rowsAffected > 0;
        } catch (Exception e) {
            log.error("Error deleting the function with code {}: {}", code, e.getMessage(), e);
            return false;
        }
    }

    private final RowMapper<Function> mapper = (rs, rowNum) -> {
        Show show = new Show();
        show.setId(rs.getString(SHOW_ID));
        show.setCode(rs.getString("show_code"));
        show.setName(rs.getString("show_name"));
        show.setDescription(rs.getString(SHOW_DESCRIPTION));

        Venue venue = new Venue();
        venue.setId(rs.getString(VENUE_ID));
        venue.setCode(rs.getString("venue_code"));
        venue.setName(rs.getString("venue_name"));
        venue.setCapacity(rs.getInt(VENUE_CAPACITY));
        venue.setLocation(rs.getString(VENUE_LOCATION));

        Function function = new Function();
        function.setId(rs.getString("function_id"));
        function.setCode(rs.getString("function_code"));
        function.setShow(show);
        function.setVenue(venue);
        function.setDate(rs.getTimestamp("function_date"));
        function.setBasePrice(rs.getDouble(FUNCTION_BASE_PRICE));
        function.setCurrency(EnumCurrency.valueOf(rs.getString(FUNCTION_CURRENCY)));

        return function;
    };
}
