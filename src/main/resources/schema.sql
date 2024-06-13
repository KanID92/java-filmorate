DROP TABLE IF EXISTS LIKES CASCADE;
DROP TABLE IF EXISTS MPA_RATING CASCADE;
DROP TABLE IF EXISTS FILM_MPA_RATING CASCADE;
DROP TABLE IF EXISTS FRIENDS CASCADE;
DROP TABLE IF EXISTS GENRES CASCADE;
DROP TABLE IF EXISTS FILM_GENRE CASCADE;
DROP TABLE IF EXISTS FILMS CASCADE;
DROP TABLE IF EXISTS USERS CASCADE;

CREATE TABLE IF NOT EXISTS MPA_RATING
(
    MPA_RATING_ID INTEGER PRIMARY KEY,
    NAME          VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS FILM_MPA_RATING
(
    FILM_ID       BIGINT  NOT NULL PRIMARY KEY,
    MPA_RATING_ID INTEGER NOT NULL
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
    DURATION_IN_MIN BIGINT       NOT NULL
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

ALTER TABLE
    FILM_MPA_RATING
    ADD CONSTRAINT "film_mpa_rating_mpa_rating_id_foreign"
        FOREIGN KEY (MPA_RATING_ID) REFERENCES MPA_RATING (MPA_RATING_ID) ON DELETE CASCADE;
ALTER TABLE
    FILM_MPA_RATING
    ADD CONSTRAINT "film_mpa_rating_films_id_foreign"
        FOREIGN KEY (FILM_ID) REFERENCES FILMS (FILM_ID) ON DELETE CASCADE;

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
