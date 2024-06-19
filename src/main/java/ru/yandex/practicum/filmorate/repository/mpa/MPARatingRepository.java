package ru.yandex.practicum.filmorate.repository.mpa;

import ru.yandex.practicum.filmorate.model.MPARating;

import java.util.Collection;

public interface MPARatingRepository {

    Collection<MPARating> getAllMPARatings();

    MPARating getMPARatingById(int mpaId);

}
