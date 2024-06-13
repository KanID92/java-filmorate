package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.MPARating;

import java.util.Collection;

public interface MPARatingRepository {

    Collection<MPARating> getAllMPARatings();

    MPARating getMPARatingById(Long mpaId);

}
