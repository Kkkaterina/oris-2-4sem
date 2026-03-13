package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<UserEntity> rowMapper = (rs, rowNum) -> {
        UserEntity user = new UserEntity();
        user.setId(UUID.fromString(rs.getString("id")));
        user.setName(rs.getString("name"));
        user.setBirthDate(rs.getObject("birth_date", LocalDate.class));
        return user;
    };

    public void save(UserEntity user) {
        String sql = "INSERT INTO users (id, name, birth_date) VALUES (:id, :name, :birthDate) " +
                "ON CONFLICT (id) DO UPDATE SET name = :name, birth_date = :birthDate";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", user.getId().toString())
                .addValue("name", user.getName())
                .addValue("birthDate", user.getBirthDate());

        jdbcTemplate.update(sql, params);
    }

    public UserEntity findById(UUID id) {
        String sql = "SELECT id, name, birth_date FROM users WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id.toString());

        try {
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (Exception e) {
            return null;
        }
    }

    public List<UserEntity> findAll() {
        String sql = "SELECT id, name, birth_date FROM users ORDER BY name";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM users WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id.toString());
        jdbcTemplate.update(sql, params);
    }
}