package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.ValidationService;
import ru.yandex.practicum.filmorate.validation.ValidationServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    final Map<Long, Film> films = new HashMap<>();
    final ValidationService validationService = new ValidationServiceImpl();


    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        log.info("==> POST /films " + film);
        validationService.validateCreate(film);
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Фильм добавлен в коллекцию.");
        log.info("<== POST /films" + film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("==> PUT /films " + film);
        validationService.validateUpdate(film, films);
        films.put(film.getId(), film);
        log.info("Информация о фильме обновлена.");
        log.info("<== PUT /films" + film);
        return film;
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}






