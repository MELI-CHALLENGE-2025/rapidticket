package com.rapidticket.function.repository;

import com.rapidticket.function.model.Show;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ShowRepositoryImpl implements ShowRepository {
    private static final String DESCRIPTION = "description";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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

    private final RowMapper<Show> mapper = (rs, rowNum) -> {
        Show show = new Show();
        show.setId(rs.getString("id"));
        show.setCode(rs.getString("code"));
        show.setName(rs.getString("name"));
        show.setDescription(rs.getString(DESCRIPTION));
        return show;
    };
}
