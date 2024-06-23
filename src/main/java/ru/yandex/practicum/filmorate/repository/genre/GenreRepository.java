package ru.yandex.practicum.filmorate.repository.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreRepository {

    Collection<Genre> getAll();

    List<Genre> getAllFilmGenres(Long filmId);

    Genre getGenreById(Integer genreId);
}
