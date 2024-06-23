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
import ru.yandex.practicum.filmorate.service.director.BaseDirectorService;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JdbcDirectorRepositoryTest {

    private final BaseDirectorService directorService;


    @Test
    public void testCreateDirector() {
        Director director = new Director(7, "Дэвид Линч");
        Director directorFromDb = directorService.save(director);
        assertThat(directorFromDb).isEqualTo(director);
    }

    @Test
    public void testUpdateDirector() {
        Director director = new Director(1, "Андрей Тарковский");
        Director directorFromDb = directorService.update(director);
        assertThat(directorFromDb).isEqualTo(director);
    }

    @Test
    public void testDeleteDirector() {
        directorService.deleteById(2);
        assertThrows(NotFoundException.class, () -> directorService.getById(2));
    }

    @Test
    public void testGetAllDirectors() {

        Collection<Director> directors = directorService.getAll();
        assertEquals(6, directors.size());
    }


    @Test
    public void testDirectorById() {
        Director director = new Director(1, "Гай Ричи");
        Director directorFromDb = directorService.getById(1);
        assertThat(directorFromDb).isEqualTo(director);
    }

}
