package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;


public class ValidateTests {
    private final FilmController filmController = new FilmController();
    private final UserController userController = new UserController();

    @Test
    public void testFilmValidAdding() throws ValidationException {
        Film film = new Film(12, "Film1", "Валидный фильм",
                "2023-01-20", 22);
        assertEquals(film, filmController.addFilm(film));
    }

    @Test
    public void testFilmWrongReleaseDate() {
        Film film = new Film(2, "Film2", "Фильм с невалидной датой релиза",
                "1895-12-20", 60);
        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void testUserValidAdding() throws ValidationException {
        User user = new User(1, "qwerty@yandex.ru", "qwerty", "Name", "1997-10-20");
        assertEquals(user, userController.addUser(user));
    }

    @Test
    public void testUserWithEmptyName() throws ValidationException {
        User user = new User( 33, "asdf@yandex.ru", "asdf", "", "1997-10-20");
        userController.addUser(user);
        assertEquals(user.getName(), user.getLogin());
    }
}