package by.vorobyov.travelagency.repository.impl.jdbc;

import by.vorobyov.travelagency.domain.Hotel;
import by.vorobyov.travelagency.repository.HotelRepository;
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
public class HotelRepositoryImpl implements HotelRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<Hotel> rowMapper;


    @Autowired
    public HotelRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        rowMapper = (resultSet, rowNum) ->
                Hotel.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .phone(resultSet.getString("phone"))
                        .country(null)
                        .stars(resultSet.getInt("stars"))
                        .build();

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Hotel> readAll() {
        String queryReadAll = "select hotel.id, hotel.name, hotel.phone, hotel.stars, hotel.country_id from hotel";
        return jdbcTemplate.query(queryReadAll, rowMapper);
    }

    @Override
    public boolean add(Hotel entity) {
        String queryAdd = "insert into hotel (name, phone, country_id, stars) VALUES (:name,:phone,:countryId,:stars)";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        try {
            return jdbcTemplate.update(queryAdd, parameterSource) > 0;
        } catch (DuplicateKeyException ex) {
            return false;
        }
    }

    @Override
    public Optional<Hotel> getEntity(long entityId) {
        String queryGetEntityById = "select hotel.id, hotel.name, hotel.phone, hotel.stars, hotel.country_id from hotel where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", entityId);
        try {
            return Optional.of(jdbcTemplate.queryForObject(queryGetEntityById, parameterSource, rowMapper));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Hotel entity) {
        String queryUpdate = "update hotel set name = :name, phone = :phone, country_id = :countryId, stars = :stars where id = :id";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        return jdbcTemplate.update(queryUpdate, parameterSource) > 0;
    }

    @Override
    public boolean delete(long entityId) {
        String queryDelete = "delete from hotel where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", entityId);
        return jdbcTemplate.update(queryDelete, parameterSource) > 0;
    }
}
