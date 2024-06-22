package repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;
import ru.yandex.practicum.filmorate.repository.film.JdbcFilmRepository;
import ru.yandex.practicum.filmorate.repository.genre.JdbcGenreRepository;
import ru.yandex.practicum.filmorate.repository.like.JdbcLikeRepository;
import ru.yandex.practicum.filmorate.service.director.BaseDirectorService;
import ru.yandex.practicum.filmorate.service.film.BaseFilmService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;

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


    public static final long testFilm1Id = 1;
    public static final String testFilm1Name = "Джентельмены";
    public static final String testFilm1Description = "USA";
    public static final LocalDate testFilm1ReleaseDate = LocalDate.parse("2019-12-03");
    public static final int testFilm1Duration = 113;
    LinkedHashSet<Genre> genreSet1 = new LinkedHashSet<>();
    public static final MPARating test1Film1MpaRating = new MPARating(5, "NC17");
    LinkedHashSet<Director> directorsSet1 = new LinkedHashSet<>();


    public static final long testFilm2Id = 2;
    public static final String testFilm2Name = "1+1";
    public static final String testFilm2Description = "France";
    public static final LocalDate testFilm2ReleaseDate = LocalDate.parse("2011-09-23");
    public static final int testFilm2Duration = 112;
    LinkedHashSet<Genre> genreSet2 = new LinkedHashSet<>();
    public static final MPARating test1Film2MpaRating = new MPARating(3, "PG13");
    LinkedHashSet<Director> directorsSet2 = new LinkedHashSet<>();

    public static final long testFilm3Id = 3;
    public static final String testFilm3Name = "Каспер";
    public static final String testFilm3Description = "USA";
    public static final LocalDate testFilm3ReleaseDate = LocalDate.parse("1995-05-26");
    public static final int testFilm3Duration = 100;
    LinkedHashSet<Genre> genreSet3 = new LinkedHashSet<>();
    public static final MPARating test1Film3MpaRating = new MPARating(1, "G");
    LinkedHashSet<Director> directorsSet3 = new LinkedHashSet<>();

    public static final long testFilm4Id = 4;
    public static final String testFilm4Name = "Титаник";
    public static final String testFilm4Description = "USA";
    public static final LocalDate testFilm4ReleaseDate = LocalDate.parse("1997-11-01");
    public static final int testFilm4Duration = 194;
    LinkedHashSet<Genre> genreSet4 = new LinkedHashSet<>();
    public static final MPARating test1Film4MpaRating = new MPARating(2, "PG");
    LinkedHashSet<Director> directorsSet4 = new LinkedHashSet<>();

    public static final long testFilm5Id = 5;
    public static final String testFilm5Name = "Загрузка: подлинная история интернета";
    public static final String testFilm5Description = "USA";
    public static final LocalDate testFilm5ReleaseDate = LocalDate.parse("2008-04-04");
    public static final int testFilm5Duration = 44;
    LinkedHashSet<Genre> genreSet5 = new LinkedHashSet<>();
    public static final MPARating test1Film5MpaRating = new MPARating(5, "R");
    LinkedHashSet<Director> directorsSet5 = new LinkedHashSet<>();

    LinkedHashSet<Director> directorsSet6 = new LinkedHashSet<>();

    @Autowired
    private BaseFilmService baseFilmService;
    @Autowired
    private BaseDirectorService baseDirectorService;


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

        directorsSet1.add(new Director(1, "Гай Ричи"));


        Film film = baseFilmService.getById(testFilm1Id);
        System.out.println(genreSet1);
        System.out.println(film);
        Film testFilm = makeTestFilm(
                testFilm1Id,
                testFilm1Name,
                testFilm1Description,
                testFilm1ReleaseDate,
                testFilm1Duration,
                genreSet1,
                test1Film1MpaRating,
                directorsSet1);
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

        Director director = new Director(7, "Спилберг");
        baseDirectorService.save(director);

        directorsSet6.add(director);

        Film newFilm = makeTestFilm(
                6,
                "Отступники",
                "USA",
                LocalDate.parse("2000-01-14"),
                241,
                genreSet2,
                new MPARating(5, "NC17"),
                directorsSet6);

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
        updatedFilm.setName("EVEN HORIZON");
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
        assertTrue(filmRepository.getById(testFilm3Id).isPresent());
        filmRepository.deleteById(testFilm3Id);
        assertThrows(NotFoundException.class, () -> filmRepository.getById(testFilm3Id));
        assertTrue(likeRepository.findAllFilmLikes(testFilm3Id).isEmpty());
        assertTrue(genreRepository.getAllFilmGenres(testFilm3Id).isEmpty());
    }

    @Test
    public void testGetTopPopular() {
        long count = 10;
        assertTrue(count >= filmRepository.getTopPopular(count).size());
    }


    public Film makeTestFilm(long id, String name, String description, LocalDate release,
                             int duration, LinkedHashSet<Genre> genres, MPARating rating,
                             LinkedHashSet<Director> directors) {
        Film testFilm = new Film();
        testFilm.setId(id);
        testFilm.setName(name);
        testFilm.setDescription(description);
        testFilm.setReleaseDate(release);
        testFilm.setDuration(duration);
        testFilm.setGenres(genres);
        testFilm.setMpa(rating);
        testFilm.setDirectors(directors);
        return testFilm;
    }
}
