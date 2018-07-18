package by.vorobyov.travelagency.repository.impl.jdbc;

import by.vorobyov.travelagency.domain.Tour;
import by.vorobyov.travelagency.domain.User;
import by.vorobyov.travelagency.repository.UserRepository;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper;

    @Autowired
    public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        userRowMapper = (resultSet, rowNum) ->
                User.builder()
                        .id(resultSet.getLong("id"))
                        .login(resultSet.getString("login"))
                        .password(resultSet.getString("password"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .build();

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> readAll() {
        String queryReadAll = "select \"user\".id, \"user\".login, \"user\".password, \"user\".name, \"user\".surname from \"user\"";
        return jdbcTemplate.query(queryReadAll, userRowMapper);
    }

    @Override
    public boolean add(User entity) {
        String queryAdd = "insert into \"user\" (login, password, name, surname) values (:login,:password,:name,:surname)";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        try {
            return jdbcTemplate.update(queryAdd, parameterSource) > 0;
        } catch (DuplicateKeyException ex) {
            return false;
        }
    }

    @Override
    public Optional<User> getEntity(long entityId) {
        String queryGetEntityById = "select \"user\".id, \"user\".login, \"user\".password, \"user\".name, \"user\".surname from \"user\" where \"user\".id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", entityId);
        try {
            return Optional.of(jdbcTemplate.queryForObject(queryGetEntityById, parameterSource, userRowMapper));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(User entity) {
        String queryUpdate = "update \"user\" set login = :login, password = :password, name = :name, surname = :surname " +
                "where id = :id";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        return jdbcTemplate.update(queryUpdate, parameterSource) > 0;
    }

    @Override
    public boolean delete(long entityId) {
        String queryDelete = "delete from \"user\" where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", entityId);
        return jdbcTemplate.update(queryDelete, parameterSource) > 0;
    }

    @Override
    public boolean addTour(long userId, long tourId) {
        String queryAddTour = "insert into user_has_tour (user_id, tour_id) values (:id, :id)";
        return tourQuery(queryAddTour, userId, tourId);
    }

    @Override
    public boolean deleteTour(long userId, long tourId) {
        String queryDeleteTour = "delete from user_has_tour where user_id = :user_ and tour_id = ?";
        return tourQuery(queryDeleteTour, userId, tourId);
    }

    @Override
    public List<Tour> tourList(long userId) {
        String queryReadAllTours = "select user_has_tour.user_id, user_has_tour.tour.id from user_has_tour where user_id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("user_id", userId);
        return jdbcTemplate.queryForList(queryReadAllTours, parameterSource, Tour.class);
    }

    private boolean tourQuery(String queryForTour, long userId, long tourId) {
        Map<String, Long> parameterMap = new HashMap<>();
        parameterMap.put("user_id", userId);
        parameterMap.put("tour_id", tourId);
        SqlParameterSource parameterSource = new MapSqlParameterSource(parameterMap);
        return jdbcTemplate.update(queryForTour, parameterSource) > 0;
    }
}
