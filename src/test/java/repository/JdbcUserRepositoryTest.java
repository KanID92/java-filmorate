package repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FriendRepository;
import ru.yandex.practicum.filmorate.repository.JdbcUserRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@SpringBootTest(classes = JdbcUserRepository.class)
//@JdbcTest
//@Import({JdbcUserRepository.class, JdbcFriendRepository.class})
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
class JdbcUserRepositoryTest {

    private final JdbcUserRepository userRepository;
    private final FriendRepository friendRepository;


    //Конcтанты
    public static final long testUser1Id = 1;
    public static final String testUser1Email = "kdv@yandex.ru";
    public static final String testUser1Login = "kdv";
    public static final String testUser1Name = "Dmitry";
    public static final LocalDate testUser1Birthday = LocalDate.parse("2001-04-01");
    public static final Set<Long> testUser1FriendsSet = Set.of(2L, 3L, 5L);

    public static final long testUser2Id = 2;
    public static final String testUser2Email = "kad@yandex.ru";
    public static final String testUser2Login = "kad";
    public static final String testUser2Name = "Igor";
    public static final LocalDate testUser2Birthday = LocalDate.parse("2004-01-01");
    public static final Set<Long> testUser2FriendsSet = Set.of(4L, 5L);

    public static final long testUser3Id = 3;
    public static final String testUser3Email = "dak@mail.ru";
    public static final String testUser3Login = "dak";
    public static final String testUser3Name = "Sergey";
    public static final LocalDate testUser3Birthday = LocalDate.parse("1993-05-01");
    public static final Set<Long> testUser3FriendsSet = null;


    public static final long testUser4Id = 4;
    public static final String testUser4Email = "zan@google.com";
    public static final String testUser4Login = "zan";
    public static final String testUser4Name = "Kirill";
    public static final LocalDate testUser4Birthday = LocalDate.parse("1989-03-02");
    public static final Set<Long> testUser4FriendsSet = null;


    public static final long testUser5Id = 5;
    public static final String testUser5Email = "zak@google.com";
    public static final String testUser5Login = "zak";
    public static final String testUser5Name = "Maksim";
    public static final LocalDate testUser5Birthday = LocalDate.parse("1934-06-17");
    public static final Set<Long> testUser5FriendsSet = null;


    @Test
    public void testFindUser1ById() {
        Optional<User> userOptional = userRepository.getById(testUser1Id);
        System.out.println(userOptional.get());
        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(getTestUser1());
    }

    @Test
    public void testFindUser2ById() {
        Optional<User> userOptional = userRepository.getById(testUser2Id);
        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(getTestUser2());
    }

    @Test
    public void testFindUser3ById() {
        Optional<User> userOptional = userRepository.getById(testUser3Id);
        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(getTestUser3());
    }

    @Test
    public void testCreateUser() {
        User newUser = userRepository.save(getTestUser6());
        assertThat(newUser).hasFieldOrPropertyWithValue("id", 6L);
        assertThat(newUser).hasFieldOrPropertyWithValue("email", "utr@yandex.ru");
        assertThat(newUser).hasFieldOrPropertyWithValue("login", "utr");
        assertThat(newUser).hasFieldOrPropertyWithValue("email", "utr@yandex.ru");
        assertThat(newUser).hasFieldOrPropertyWithValue("name", "Uri");
        assertThat(newUser).hasFieldOrPropertyWithValue("birthday", LocalDate.parse("1992-02-22"));

    }

    @Test
    public void testGetAllUsers() {
        Collection<User> users = userRepository.getAll();
        assertEquals(5, users.size());
    }


    @Test
    public void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setId(4);
        updatedUser.setEmail("123f@mail.ru");
        updatedUser.setLogin("123f");
        updatedUser.setName("Igor");
        updatedUser.setBirthday(LocalDate.parse("1992-08-02"));

        User updatedUserFromBd = userRepository.update(updatedUser);

        assertThat(updatedUserFromBd)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(updatedUser);
    }

    @Test
    public void testDeleteUser() {
        assertTrue(userRepository.getById(testUser1Id).isPresent());
        userRepository.deleteById(testUser1Id);
        assertTrue(userRepository.getById(testUser1Id).isEmpty());
        assertTrue(friendRepository.getFriendsIds(testUser1Id).isEmpty());
    }


    User getTestUser1() {
        User user = new User();
        user.setId(testUser1Id);
        user.setEmail(testUser1Email);
        user.setLogin(testUser1Login);
        user.setName(testUser1Name);
        user.setBirthday(testUser1Birthday);
        user.setFrindsSet(testUser1FriendsSet);
        return user;
    }

    User getTestUser2() {
        User user = new User();
        user.setId(testUser2Id);
        user.setEmail(testUser2Email);
        user.setLogin(testUser2Login);
        user.setName(testUser2Name);
        user.setBirthday(testUser2Birthday);
        user.setFrindsSet(testUser2FriendsSet);
        return user;
    }

    User getTestUser3() {
        User user = new User();
        user.setId(testUser3Id);
        user.setEmail(testUser3Email);
        user.setLogin(testUser3Login);
        user.setName(testUser3Name);
        user.setBirthday(testUser3Birthday);
        user.setFrindsSet(testUser3FriendsSet);
        return user;
    }

    User getTestUser4() {
        User user = new User();
        user.setId(testUser4Id);
        user.setEmail(testUser4Email);
        user.setLogin(testUser4Login);
        user.setName(testUser4Name);
        user.setBirthday(testUser4Birthday);
        return user;
    }

    User getTestUser5() {
        User user = new User();
        user.setId(testUser5Id);
        user.setEmail(testUser5Email);
        user.setLogin(testUser5Login);
        user.setName(testUser5Name);
        user.setBirthday(testUser5Birthday);
        return user;
    }

    User getTestUser6() {
        User user = new User();
        user.setId(6);
        user.setEmail("utr@yandex.ru");
        user.setLogin("utr");
        user.setName("Uri");
        user.setBirthday(LocalDate.parse("1992-02-22"));
        return user;
    }


}
