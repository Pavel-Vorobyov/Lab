package by.vorobyov.travelagency.repository.impl.jdbc;

import by.vorobyov.travelagency.domain.Review;
import by.vorobyov.travelagency.repository.ReviewRepository;
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
public class ReviewRepositoryImpl implements ReviewRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<Review> rowMapper;

    @Autowired
    public ReviewRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        rowMapper = (resultSet, rowNum) ->
                Review.builder()
                        .id(resultSet.getInt("id"))
                        .tour(null)
                        .user(null)
                        .content(resultSet.getString("content"))
                        .build();

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Review> readAll() {
        String queryReadAll = "select review.id, review.content, review.tour_id, review.user_id from review";
        return jdbcTemplate.query(queryReadAll, rowMapper);
    }

    @Override
    public boolean add(Review entity) {
        String queryAdd = "insert into review (tour_id, user_id, content) values (:tourId,:userId,:content)";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        try {
            return jdbcTemplate.update(queryAdd, parameterSource) > 0;
        } catch (DuplicateKeyException ex) {
            return false;
        }
    }

    @Override
    public Optional<Review> getEntity(long entityId) {
        String queryGetEntityById = "select review.id, review.content, review.tour_id, review.user_id from review where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", entityId);
        try {
            return Optional.of(jdbcTemplate.queryForObject(queryGetEntityById, parameterSource, rowMapper));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Review entity) {
        String queryUpdate = "update review set tour_id = :tourId, user_id = :userId, content = :content where id = :id";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        return jdbcTemplate.update(queryUpdate, parameterSource) > 0;
    }

    @Override
    public boolean delete(long entityId) {
        String queryDelete = "delete from review where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", entityId);
        return jdbcTemplate.update(queryDelete, parameterSource) > 0;
    }
}
