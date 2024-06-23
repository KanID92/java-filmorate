package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(of = "id")
public class Feed {
    private long id;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;
    private EventType eventType;
    private EventOperation operation;
    private long entityId;
    private long userId;
}
