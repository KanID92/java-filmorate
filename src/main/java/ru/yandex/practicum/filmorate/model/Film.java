package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


/**
 * Film.
 */
@Data
public class Film {
    long id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration; // Длительность в минутах
    Set<Long> usersLikes = new HashSet<>();
}
