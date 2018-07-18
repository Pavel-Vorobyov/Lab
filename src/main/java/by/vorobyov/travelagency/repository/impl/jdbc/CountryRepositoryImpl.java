package by.vorobyov.travelagency.repository.impl.jdbc;

import by.vorobyov.travelagency.domain.Country;
import by.vorobyov.travelagency.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class CountryRepositoryImpl implements CountryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<Country> rowMapper;

    @Autowired
    public CountryRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        rowMapper = (resultSet, rowNum) ->
                Country.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Country> readAll() {
        String queryReadAll = "select country.id, country.name from country";
        return jdbcTemplate.query(queryReadAll, rowMapper);
    }

    @Override
    public boolean add(Country entity) {
        String queryAdd = "insert into country (name) values (:name)";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        try {
            return jdbcTemplate.update(queryAdd, parameterSource) > 0;
        } catch (DuplicateKeyException ex) {
            return false;
        }
    }

    @Override
    public Optional<Country> getEntity(long entityId) {
        String queryGetEntityById = "select country.id, country.name from country where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", entityId);
        try {
            return Optional.of(jdbcTemplate.queryForObject(queryGetEntityById, parameterSource, rowMapper));
        } catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Country entity) {
        String queryUpdate = "update country set name = :name where id = :id";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        return jdbcTemplate.update(queryUpdate, parameterSource) > 0;
    }

    @Override
    public boolean delete(long entityId) {
        String queryDelete = "delete from country where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", entityId);
        return jdbcTemplate.update(queryDelete, parameterSource) > 0;
    }
}


