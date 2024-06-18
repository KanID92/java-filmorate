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
INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION_IN_MIN)
VALUES ('Джентельмены', 'USA', '2019-12-03', 113),
       ('1+1', 'France', '2011-09-23', 112),
       ('Каспер', 'USA', '1995-05-26', 100),
       ('Титаник', 'USA', '1997-11-01', 194),
       ('Загрузка: подлинная история интернета', 'USA', '2008-04-04', 44);

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

-- MPA-RATING
INSERT INTO FILM_MPA_RATING (FILM_ID, MPA_RATING_ID)
VALUES (1, 5),
       (2, 3),
       (3, 1),
       (4, 2),
       (5, 4);

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






