--MPA-RATING AND GENRES
INSERT INTO MPA_RATING (MPA_RATING_ID, NAME)
VALUES (1, 'G'),
       (2, 'PG'),
       (3, 'PG13'),
       (4, 'R'),
       (5, 'NC17');

INSERT INTO GENRES (GENRE_NAME)
VALUES ('Комедия'),
       ('Драма'),
       ('Мультфильм'),
       ('Триллер'),
       ('Документальный'),
       ('Боевик');

-- USERS

INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY)
VALUES ('kdv@yandex.ru', 'kdv', 'Dmitry', '2001-04-01'),
       ('kad@yandex.ru', 'kad', 'Igor', '2004-01-01'),
       ('dak@mail.ru', 'dak', 'Sergey', '1993-05-01'),
       ('zan@google.com', 'zan', 'Kirill', '1989-03-02'),
       ('zak@google.com', 'zak', 'Maksim', '1934-06-17');

--FRIENDS
INSERT INTO FRIENDS (USER_ID, FRIEND_ID)
VALUES (1, 2),
       (1, 5),
       (2, 4),
       (2, 5),
       (1, 3),
       (3, 4),
       (3, 5);


--FILMS
INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION_IN_MIN, MPA_RATING_ID)
VALUES ('Джентельмены', 'USA', '2019-12-03', 113, 5),
       ('1+1', 'France', '2011-09-23', 112, 3),
       ('Каспер', 'USA', '1995-05-26', 100, 1),
       ('Титаник', 'USA', '1997-11-01', 194, 2),
       ('Загрузка: подлинная история интернета', 'USA', '2008-04-04', 44, 4);

--FILMS -> GENRES
INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID)
VALUES (1, 1),
       (1, 6),
       (2, 1),
       (2, 2),
       (3, 3),
       (4, 2),
       (4, 4),
       (4, 5),
       (5, 5);

--LIKES
INSERT INTO LIKES (FILM_ID, USER_ID)
VALUES (1, 2),
       (5, 2),
       (2, 1),
       (4, 1),
       (2, 3),
       (5, 4),
       (2, 4),
       (3, 5);

--REVIEW
INSERT INTO REVIEWS (CONTENT, IS_POSITIVE, USER_ID, FILM_ID)
VALUES ('test1', true, 1, 1),
       ('test2', true, 2, 1),
       ('test3', true, 3, 2),
       ('test4', true, 4, 2);

--REVIEWS_LIKES
INSERT INTO REVIEWS_LIKES (REVIEW_ID, USER_ID, SCORE)
VALUES (1, 3, 1),
       (1, 4, 1),
       (1, 2, 1),
       (2, 3, -1),
       (3, 1, 1);

--DIRECTORS
INSERT INTO DIRECTORS (NAME)
VALUES ('Гай Ричи'),
       ('Оливье Накаш'),
       ('Эрик Толедано'),
       ('Иззи Спарбер'),
       ('Ник Тафури'),
       ('Джеймс Кэмерон');


INSERT INTO FILM_DIRECTOR (FILM_ID, DIRECTOR_ID)
VALUES (1, 1),
       (2, 2),
       (2, 3),
       (3, 4),
       (3, 5),
       (4, 6);
INSERT INTO EVENT_TYPES (NAME)
VALUES ('LIKE'),
       ('REVIEW'),
       ('FRIEND');

INSERT INTO EVENT_OPERATIONS (NAME)
VALUES ('REMOVE'),
       ('ADD'),
       ('UPDATE');
INSERT INTO FEEDS (USER_ID, EVENT_TYPE, OPERATION, ENTITY_ID)
VALUES (1,1,2,1 ),
       (1,3,2,2);
