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
    public static final long TEST_USER1_ID = 1;
    public static final String TEST_USER1_EMAIL = "kdv@yandex.ru";
    public static final String TEST_USER1_LOGIN = "kdv";
    public static final String TEST_USER1_NAME = "Dmitry";
    public static final LocalDate TEST_USER1_BIRTHDAY = LocalDate.parse("2001-04-01");
    public static final Set<Long> TEST_USER_1_FRIENDS_SET = Set.of(2L, 3L, 5L);

    public static final long TEST_USER2_ID = 2;
    public static final String TEST_USER2_EMAIL = "kad@yandex.ru";
    public static final String TEST_USER2_LOGIN = "kad";
    public static final String TEST_USER2_NAME = "Igor";
    public static final LocalDate TEST_USER2_BIRTHDAY = LocalDate.parse("2004-01-01");
    public static final Set<Long> TEST_USER2_FRIENDS_SET = Set.of(4L, 5L);

    public static final long TEST_USER3_ID = 3;
    public static final String TEST_USER3_EMAIL = "dak@mail.ru";
    public static final String TEST_USER3_LOGIN = "dak";
    public static final String TEST_USER3_NAME = "Sergey";
    public static final LocalDate TEST_USER3_BIRTHDAY = LocalDate.parse("1993-05-01");
    public static final Set<Long> TEST_USER3_FRIEND_SET = null;


    public static final long TEST_USER4_ID = 4;
    public static final String TEST_USER4_EMAIL = "zan@google.com";
    public static final String TEST_USER4_LOGIN = "zan";
    public static final String TEST_USER4_NAME = "Kirill";
    public static final LocalDate TEST_USER4_BIRTHDAY = LocalDate.parse("1989-03-02");
    public static final Set<Long> TEST_USER4_FRIENDS_SET = null;


    public static final long TEST_USER5_ID = 5;
    public static final String TEST_USER5_EMAIL = "zak@google.com";
    public static final String TEST_USER5_LOGIN = "zak";
    public static final String TEST_USER5_NAME = "Maksim";
    public static final LocalDate TEST_USER5_BIRTHDAY = LocalDate.parse("1934-06-17");
    public static final Set<Long> TEST_USER5_FRIENDS_SET = null;


    @Test
    public void testFindUser1ById() {
        Optional<User> userOptional = userRepository.getById(TEST_USER1_ID);
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
        Optional<User> userOptional = userRepository.getById(TEST_USER2_ID);
        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(getTestUser2());
    }

    @Test
    public void testFindUser3ById() {
        Optional<User> userOptional = userRepository.getById(TEST_USER3_ID);
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
        assertTrue(userRepository.getById(TEST_USER1_ID).isPresent());
        userRepository.deleteById(TEST_USER1_ID);
        assertTrue(userRepository.getById(TEST_USER1_ID).isEmpty());
        assertTrue(friendRepository.getFriendsIds(TEST_USER1_ID).isEmpty());
    }


    User getTestUser1() {
        User user = new User();
        user.setId(TEST_USER1_ID);
        user.setEmail(TEST_USER1_EMAIL);
        user.setLogin(TEST_USER1_LOGIN);
        user.setName(TEST_USER1_NAME);
        user.setBirthday(TEST_USER1_BIRTHDAY);
        user.setFrindsSet(TEST_USER_1_FRIENDS_SET);
        return user;
    }

    User getTestUser2() {
        User user = new User();
        user.setId(TEST_USER2_ID);
        user.setEmail(TEST_USER2_EMAIL);
        user.setLogin(TEST_USER2_LOGIN);
        user.setName(TEST_USER2_NAME);
        user.setBirthday(TEST_USER2_BIRTHDAY);
        user.setFrindsSet(TEST_USER2_FRIENDS_SET);
        return user;
    }

    User getTestUser3() {
        User user = new User();
        user.setId(TEST_USER3_ID);
        user.setEmail(TEST_USER3_EMAIL);
        user.setLogin(TEST_USER3_LOGIN);
        user.setName(TEST_USER3_NAME);
        user.setBirthday(TEST_USER3_BIRTHDAY);
        user.setFrindsSet(TEST_USER3_FRIEND_SET);
        return user;
    }

    User getTestUser4() {
        User user = new User();
        user.setId(TEST_USER4_ID);
        user.setEmail(TEST_USER4_EMAIL);
        user.setLogin(TEST_USER4_LOGIN);
        user.setName(TEST_USER4_NAME);
        user.setBirthday(TEST_USER4_BIRTHDAY);
        return user;
    }

    User getTestUser5() {
        User user = new User();
        user.setId(TEST_USER5_ID);
        user.setEmail(TEST_USER5_EMAIL);
        user.setLogin(TEST_USER5_LOGIN);
        user.setName(TEST_USER5_NAME);
        user.setBirthday(TEST_USER5_BIRTHDAY);
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
