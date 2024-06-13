package ru.yandex.practicum.filmorate.model;

import lombok.Data;


@Data
public class MPARating {

    private int id;
    private String name;

    public MPARating() {

    }

    public MPARating(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
