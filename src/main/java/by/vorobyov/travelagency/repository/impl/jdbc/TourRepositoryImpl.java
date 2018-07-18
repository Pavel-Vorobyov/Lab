package by.vorobyov.travelagency.repository.impl.jdbc;

import by.vorobyov.travelagency.domain.Tour;
import by.vorobyov.travelagency.repository.TourRepository;
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
public class TourRepositoryImpl implements TourRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<Tour> rowMapper;

    @Autowired
    public TourRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        rowMapper = (resultSet, rowNum) ->
                Tour.builder()
                        .id(resultSet.getInt("id"))
                        .photo(resultSet.getString("photo"))
                        .date(resultSet.getDate("date").toLocalDate())
                        .duration(resultSet.getInt("duration"))
                        .country(null)
                        .hotel(null)
                        .type(null)
                        .description(resultSet.getString("description"))
                        .coast(resultSet.getDouble("coast"))
                        .build();

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tour> readAll() {
        String queryReadAll = "select tour.id, tour.photo, tour.date, tour.duration, tour.country_id" +
                ", tour.hotel_id, tour.type_id, tour.description, tour.coast from tour";
        return jdbcTemplate.query(queryReadAll, rowMapper);
    }

    @Override
    public boolean add(Tour entity) {
        String queryAdd = "insert into tour (photo, date, duration, type_id, description, coast, " +
                "country_id, hotel_id) VALUES (:photo,:date,:duration,:typeId,:description,:coast,:countryId,:hotelId)";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        try {
            return jdbcTemplate.update(queryAdd, parameterSource) > 0;
        } catch (DuplicateKeyException ex) {
            return false;
        }
    }

    @Override
    public Optional<Tour> getEntity(long entityId) {
        String queryGetEntityById = "select tour.id, tour.photo, tour.date, tour.duration, tour.country_id" +
                ", tour.hotel_id, tour.type_id, tour.description, tour.coast from tour where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", entityId);
        try {
            return Optional.of(jdbcTemplate.queryForObject(queryGetEntityById, parameterSource, rowMapper));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Tour entity) {
        String queryUpdate = "update tour set photo = :photo, date = :date, duration = :duration, type_id = :typeId, " +
                "description = :description, coast = :coast, country_id = :countryId, hotel_id = :hotelId where id = :id";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        return jdbcTemplate.update(queryUpdate, parameterSource) > 0;
    }

    @Override
    public boolean delete(long entityId) {
        String queryDelete = "delete from tour where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", entityId);
        return jdbcTemplate.update(queryDelete, parameterSource) > 0;
    }
}
