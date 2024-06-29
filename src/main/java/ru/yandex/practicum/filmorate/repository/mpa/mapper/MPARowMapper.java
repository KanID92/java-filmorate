package ru.yandex.practicum.filmorate.repository.mpa.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MPARowMapper implements RowMapper<MPARating> {

    @Override
    public MPARating mapRow(ResultSet rs, int rowNum) throws SQLException {
        String mpaRatingFromDb = rs.getString("name");

        return new MPARating(rs.getInt("mpa_rating_id"), mpaRatingFromDb);

    }
}
