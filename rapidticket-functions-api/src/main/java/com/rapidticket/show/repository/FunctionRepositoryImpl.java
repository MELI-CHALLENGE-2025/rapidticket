package com.rapidticket.show.repository;

import com.rapidticket.show.domain.dto.request.FunctionListRequestDTO;
import com.rapidticket.show.model.Show;
import com.rapidticket.show.model.Venue;
import com.rapidticket.show.utils.enums.EnumCurrency;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.rapidticket.show.model.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FunctionRepositoryImpl implements FunctionRepository {
    private static final String SHOW_ID = "show_id";
    private static final String VENUE_ID = "venue_id";
    private static final String BASE_PRICE = "base_price";
    private static final String CURRENCY = "currency";

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
                    .addValue(BASE_PRICE, function.getBasePrice())
                    .addValue(CURRENCY, function.getCurrency().name(), Types.OTHER);

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

            StringBuilder sql = new StringBuilder("SELECT id, code, show_id, venue_id, date, base_price, currency, created_at FROM functions WHERE 1=1 ");
            MapSqlParameterSource params = new MapSqlParameterSource();

            if (dto.getCode() != null && !dto.getCode().isEmpty()) {
                sql.append("AND code LIKE :code ");
                params.addValue("code", "%" + dto.getCode() + "%");
            }

            if (dto.getShowCode() != null && !dto.getShowCode().isEmpty()) {
                sql.append("AND show_id LIKE :showId ");
                params.addValue("showId", "%" + dto.getShowCode() + "%");
            }

            if (dto.getVenueCode() != null && !dto.getVenueCode().isEmpty()) {
                sql.append("AND venue_id LIKE :venueId ");
                params.addValue("venueId", "%" + dto.getVenueCode() + "%");
            }

            if (dto.getMinBasePrice() != null) {
                sql.append("AND base_price >= :minBasePrice ");
                params.addValue("minBasePrice", dto.getMinBasePrice());
            }

            if (dto.getMaxBasePrice() != null) {
                sql.append("AND base_price <= :maxBasePrice ");
                params.addValue("maxBasePrice", dto.getMaxBasePrice());
            }

            if (dto.getCurrency() != null && !dto.getCurrency().isEmpty()) {
                sql.append("AND currency LIKE :currency ");
                params.addValue(CURRENCY, "%" + dto.getCurrency() + "%");
            }

            sql.append("ORDER BY created_at ASC LIMIT :size OFFSET :offset");
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
            String sql = "SELECT * FROM functions WHERE code = :code";
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
                    .addValue(SHOW_ID, entity.getShowId())
                    .addValue(VENUE_ID, entity.getVenueId())
                    .addValue("date", entity.getDate())
                    .addValue(BASE_PRICE, entity.getBasePrice())
                    .addValue(CURRENCY, entity.getCurrency())
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
        Function function = new Function();
        function.setId(rs.getString("id"));
        function.setCode(rs.getString("code"));
        function.setShowId(rs.getString(SHOW_ID));
        function.setVenueId(rs.getString(VENUE_ID));
        function.setDate(rs.getDate("date"));
        function.setBasePrice(rs.getDouble(BASE_PRICE));
        function.setCurrency(EnumCurrency.valueOf(rs.getString(CURRENCY)));

        return function;
    };
}
