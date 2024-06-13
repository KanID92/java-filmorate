package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreRepository {

    Collection<Genre> getAll();

    List<Genre> getAllFilmGenres(Long film_id);

    Genre getGenreById(Integer genreId);

}
