package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPARating;
import ru.yandex.practicum.filmorate.repository.mapper.MPARowMapper;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcMPARatingRepository implements MPARatingRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Collection<MPARating> getAllMPARatings() {
        String sql = "select * from mpa_rating";
        List<MPARating> mpaRatingList = jdbc.query(sql, new MPARowMapper());
        HashMap<Integer, MPARating> mpaRatingMap = new HashMap<>();
        for (MPARating mpaRating : mpaRatingList) {
            mpaRatingMap.put(mpaRating.getId(), mpaRating);
        }

        return new LinkedHashSet<>(mpaRatingMap.values());

//        Map<Integer, MPARating> map = new HashMap<>();
//        Map<String, Object> mapJdbc = jdbc.queryForMap(sql, Map.of());
//        for (String key : mapJdbc.keySet()) {
//            int newKey = Integer.parseInt(key);
//            MPARating newMpaRating = new MPARating(newKey, (String) mapJdbc.get(key));
//            map.put(newKey, newMpaRating);
//        }
//        return map;
    }

    @Override
    public MPARating getMPARatingById(Long mpaId) {
        try {
            String sql = "SELECT * FROM mpa_rating WHERE mpa_rating_id = :mpaId";
//        String sql = "select mr.name from film_mpa_rating AS fmr where film_id = :film_id " +
//                "LEFT JOIN mpa_rating AS mr ON fmr.mpa_rating_id = mr.mpa_rating_id";

            return jdbc.queryForObject(sql, Map.of("mpaId", mpaId), new MPARowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("No MPARating with id " + mpaId);
        }
    }


}
