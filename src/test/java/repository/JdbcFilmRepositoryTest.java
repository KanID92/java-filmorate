package repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;
import ru.yandex.practicum.filmorate.repository.JdbcFilmRepository;
import ru.yandex.practicum.filmorate.repository.JdbcGenreRepository;
import ru.yandex.practicum.filmorate.repository.JdbcLikeRepository;
import ru.yandex.practicum.filmorate.service.BaseFilmService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JdbcFilmRepositoryTest {

    public final JdbcFilmRepository filmRepository;
    public final JdbcLikeRepository likeRepository;
    public final JdbcGenreRepository genreRepository;


    public static final long TEST_FILM1_ID = 1;
    public static final String TEST_FILM1_NAME = "Джентельмены";
    public static final String TEST_FILM1_DESCRIPTION = "USA";
    public static final LocalDate TEST_FILM1_RELEASE_DATE = LocalDate.parse("2019-12-03");
    public static final int TEST_FILM1_DURATION_IN_MIN = 113;
    LinkedHashSet<Genre> genreSet1 = new LinkedHashSet<>();
    public LinkedHashSet<Genre> TEST_FILM1_GENRESET = new LinkedHashSet<>();
    public static final MPARating TEST_FILM1_MPARATING = new MPARating(5, "NC17");


    public static final long TEST_FILM2_ID = 2;
    public static final String TEST_FILM2_NAME = "1+1";
    public static final String TEST_FILM2_DESCRIPTION = "France";
    public static final LocalDate TEST_FILM2_RELEASE_DATE = LocalDate.parse("2011-09-23");
    public static final int TEST_FILM2_DURATION_IN_MIN = 112;
    public static final Set<Genre> TEST_FILM2_GENRESET = new LinkedHashSet<>();
    //        Set.of(new Genre(1,"COMEDY"), new Genre(2, "DRAMA")));
    public static final MPARating TEST_FILM2_MPARATING = new MPARating(3, "PG13");

    public static final long TEST_FILM3_ID = 3;
    public static final String TEST_FILM3_NAME = "Каспер";
    public static final String TEST_FILM3_DESCRIPTION = "USA";
    public static final LocalDate TEST_FILM3_RELEASE_DATE = LocalDate.parse("1995-05-26");
    public static final int TEST_FILM3_DURATION_IN_MIN = 100;
    public static final Set<Genre> TEST_FILM3_GENRESET = new LinkedHashSet<>();
    //        Set.of(new Genre(3,"CARTOON")));
    public static final MPARating TEST_FILM3_MPARATING = new MPARating(1, "G");

    public static final long TEST_FILM4_ID = 4;
    public static final String TEST_FILM4_NAME = "Титаник";
    public static final String TEST_FILM4_DESCRIPTION = "USA";
    public static final LocalDate TEST_FILM4_RELEASE_DATE = LocalDate.parse("1997-11-01");
    public static final int TEST_FILM4_DURATION_IN_MIN = 194;
    public static final Set<Genre> TEST_FILM4_GENRESET = new LinkedHashSet<>();
    //        Set.of(new Genre(2, "DRAMA"), new Genre(5,"THRILLER"), new Genre(6,"ACTION")));
    public static final MPARating TEST_FILM4_MPARATING = new MPARating(2, "PG");

    public static final long TEST_FILM5_ID = 5;
    public static final String TEST_FILM5_NAME = "Загрузка: подлинная история интернета";
    public static final String TEST_FILM5_DESCRIPTION = "USA";
    public static final LocalDate TEST_FILM5_RELEASE_DATE = LocalDate.parse("2008-04-04");
    public static final int TEST_FILM5_DURATION_IN_MIN = 44;
    public static final Set<Genre> TEST_FILM5_GENRESET = new LinkedHashSet<>();
    //        Set.of(new Genre(5, "DOCUMENTARY")));
    public static final MPARating TEST_FILM5_MPARATING = new MPARating(5, "R");
    @Autowired
    private BaseFilmService baseFilmService;


    @Test
    public void testFindFilmById() {
        Genre genre1 = new Genre();
        Genre genre2 = new Genre();
        genre1.setId(1);
        genre1.setName("Комедия");
        genre2.setId(6);
        genre2.setName("Боевик");

        genreSet1.add(genre1);
        genreSet1.add(genre2);

        Film film = baseFilmService.getById(TEST_FILM1_ID);
        System.out.println(genreSet1);
        System.out.println(film);
        Film testFilm = makeTestFilm(
                TEST_FILM1_ID,
                TEST_FILM1_NAME,
                TEST_FILM1_DESCRIPTION,
                TEST_FILM1_RELEASE_DATE,
                TEST_FILM1_DURATION_IN_MIN,
                genreSet1,
                TEST_FILM1_MPARATING);
        testFilm.setGenres(genreSet1);
        assertThat(film)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(testFilm);
    }

    @Test
    public void testCreateFilm() {

        LinkedHashSet<Genre> genreSet2 = new LinkedHashSet<>();
        Genre genre1 = new Genre();
        Genre genre2 = new Genre();
        genre1.setId(2);
        genre1.setName("Драма");
        genre2.setId(6);
        genre2.setName("Боевик");

        genreSet2.add(genre1);
        genreSet2.add(genre2);

        Film newFilm = makeTestFilm(
                6,
                "Отступники",
                "USA",
                LocalDate.parse("2000-01-14"),
                241,
                genreSet2,
                new MPARating(5, "NC17"));

        baseFilmService.save(newFilm);
        Film filmFromDB = baseFilmService.getById(6);
        assertThat(filmFromDB)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(newFilm);
    }

    @Test
    public void testGetAllFilms() {
        Collection<Film> films = filmRepository.getAll();
        assertEquals(5, films.size());
    }

    @Test
    public void testUpdateFilm() {
        Film updatedFilm = new Film();
        updatedFilm.setId(1);
        updatedFilm.setName("EVEN_HORIZON");
        updatedFilm.setDescription("UK");
        updatedFilm.setReleaseDate(LocalDate.parse("1954-08-01"));
        updatedFilm.setDuration(72);
        LinkedHashSet<Genre> genreSet = new LinkedHashSet<>();
        Genre genre1 = new Genre();
        Genre genre2 = new Genre();
        genre1.setId(4);
        genre1.setName("Триллер");
        genre2.setId(5);
        genre2.setName("Документальный");
        updatedFilm.setGenres(genreSet);
        updatedFilm.setMpa(new MPARating(2, "PG"));

        Film updatedFilmFromBd = baseFilmService.update(updatedFilm);

        assertThat(updatedFilmFromBd)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(updatedFilm);
    }

    @Test
    public void testDeleteFilm() {
        assertTrue(filmRepository.getById(TEST_FILM3_ID).isPresent());
        filmRepository.deleteById(TEST_FILM3_ID);
        assertThrows(NotFoundException.class, () -> filmRepository.getById(TEST_FILM3_ID));
        assertTrue(likeRepository.findAllFilmLikes(TEST_FILM3_ID).isEmpty());
        assertTrue(genreRepository.getAllFilmGenres(TEST_FILM3_ID).isEmpty());
    }

    @Test
    public void testGetTopPopular() {
        long count = 10;
        assertTrue(count >= filmRepository.getTopPopular(count).size());
    }


    public Film makeTestFilm(long id, String name, String description, LocalDate release,
                             int duration, LinkedHashSet<Genre> genres, MPARating rating) {
        Film testFilm = new Film();
        testFilm.setId(id);
        testFilm.setName(name);
        testFilm.setDescription(description);
        testFilm.setReleaseDate(release);
        testFilm.setDuration(duration);
        testFilm.setGenres(genres);
        testFilm.setMpa(rating);
        return testFilm;
    }
}
