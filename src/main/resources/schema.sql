DROP TABLE IF EXISTS LIKES CASCADE;
DROP TABLE IF EXISTS MPA_RATING CASCADE;
DROP TABLE IF EXISTS FRIENDS CASCADE;
DROP TABLE IF EXISTS GENRES CASCADE;
DROP TABLE IF EXISTS FILM_GENRE CASCADE;
DROP TABLE IF EXISTS FILMS CASCADE;
DROP TABLE IF EXISTS USERS CASCADE;
DROP TABLE IF EXISTS REVIEWS CASCADE;
DROP TABLE IF EXISTS REVIEWS_LIKES CASCADE;
DROP TABLE IF EXISTS FEEDS CASCADE;
DROP TABLE IF EXISTS EVENT_OPERATIONS CASCADE;
DROP TABLE IF EXISTS EVENT_TYPES CASCADE;

CREATE TABLE IF NOT EXISTS MPA_RATING
(
    MPA_RATING_ID INTEGER NOT NULL UNIQUE PRIMARY KEY,
    NAME          VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID  BIGINT UNIQUE PRIMARY KEY AUTO_INCREMENT,
    EMAIL    VARCHAR(255) NOT NULL UNIQUE,
    LOGIN    VARCHAR(255) NOT NULL UNIQUE,
    NAME     VARCHAR(255) NOT NULL,
    BIRTHDAY DATE
);

CREATE TABLE IF NOT EXISTS FRIENDS
(
    USER_ID   BIGINT,
    FRIEND_ID BIGINT,
    PRIMARY KEY (USER_ID, FRIEND_ID)

);


CREATE TABLE IF NOT EXISTS GENRES
(
    GENRE_ID   INT PRIMARY KEY AUTO_INCREMENT,
    GENRE_NAME VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS FILMS
(
    FILM_ID         BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME            VARCHAR(255) NOT NULL,
    DESCRIPTION     VARCHAR(255) NOT NULL,
    RELEASE_DATE    DATE         NOT NULL,
    DURATION_IN_MIN BIGINT  NOT NULL,
    MPA_RATING_ID   INTEGER NOT NULL
);


CREATE TABLE IF NOT EXISTS LIKES
(
    FILM_ID BIGINT NOT NULL,
    USER_ID BIGINT NOT NULL,
    PRIMARY KEY (FILM_ID, USER_ID)
);

CREATE TABLE IF NOT EXISTS FILM_GENRE
(
    FILM_GENRE_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    FILM_ID       BIGINT  NOT NULL,
    GENRE_ID      INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS reviews
(
    review_id   INTEGER auto_increment,
    content     VARCHAR(155) NOT NULL,
    is_positive BOOLEAN      NOT NULL,
    user_id     INTEGER      NOT NULL,
    film_id     INTEGER      NOT NULL,
    constraint reviews_pk
        primary key (review_id),
    constraint reviews_FILMS_FILM_ID_fk
        foreign key (film_id) references FILMS ON DELETE CASCADE,
    constraint reviews_USERS_USER_ID_fk
        foreign key (user_id) references USERS ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews_likes
(
    review_id INTEGER NOT NULL,
    user_id   INTEGER NOT NULL,
    score     INTEGER NOT NULL,
    constraint REVIEWS_LIKES_PK
        primary key (REVIEW_ID, USER_ID),
    constraint reviews_likes_REVIEWS_REVIEW_ID_fk
        foreign key (review_id) references REVIEWS ON DELETE CASCADE,
    constraint reviews_likes_USERS_USER_ID_fk
        foreign key (user_id) references USERS ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS event_types
(
    types_id INTEGER auto_increment,
    name     VARCHAR(15) not null,
    constraint event_types_pk
        primary key (types_id)
);
CREATE TABLE IF NOT EXISTS event_operations
(
    operation_id INTEGER auto_increment,
    name         VARCHAR(15),
    constraint event_operations_pk
        primary key (operation_id)
);
CREATE TABLE IF NOT EXISTS feeds
(
    event_id   INTEGER auto_increment,
    timestamp  TIMESTAMP    default current_timestamp ,
    user_id    INTEGER not null,
    event_type INTEGER not null,
    operation  INTEGER not null,
    entity_id  INTEGER not null,
    constraint feeds_pk
        primary key (event_id),
    constraint feeds_EVENT_OPERATIONS_OPERATION_ID_fk
        foreign key (operation) references EVENT_OPERATIONS,
    constraint feeds_EVENT_TYPES_TYPES_ID_fk
        foreign key (event_type) references EVENT_TYPES,
    constraint feeds_USERS_USER_ID_fk
        foreign key (user_id) references USERS ON DELETE CASCADE
);

ALTER TABLE
    FILMS
    ADD CONSTRAINT "film_mpa_rating_id_foreign"
        FOREIGN KEY (MPA_RATING_ID) REFERENCES MPA_RATING (MPA_RATING_ID) ON DELETE CASCADE;

ALTER TABLE
    LIKES
    ADD CONSTRAINT "likes_user_id_foreign"
        FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID) ON DELETE CASCADE;
ALTER TABLE
    FILM_GENRE
    ADD CONSTRAINT "film_genre_film_id_foreign"
        FOREIGN KEY (FILM_ID) REFERENCES FILMS (FILM_ID) ON DELETE CASCADE;
ALTER TABLE
    LIKES
    ADD CONSTRAINT "likes_film_id_foreign"
        FOREIGN KEY (FILM_ID) REFERENCES FILMS (FILM_ID) ON DELETE CASCADE;
ALTER TABLE
    FILM_GENRE
    ADD CONSTRAINT "film_genre_genre_id_foreign"
        FOREIGN KEY (GENRE_ID) REFERENCES GENRES (GENRE_ID) ON DELETE CASCADE;
ALTER TABLE
    FRIENDS
    ADD CONSTRAINT "friends_user_id_confirming_foreign"
        FOREIGN KEY (FRIEND_ID) REFERENCES USERS (USER_ID) ON DELETE CASCADE;
ALTER TABLE
    FRIENDS
    ADD CONSTRAINT "friends_user_id_adding_foreign"
        FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID) ON DELETE CASCADE;
