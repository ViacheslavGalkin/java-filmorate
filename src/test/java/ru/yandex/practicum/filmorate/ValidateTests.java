package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;


public class ValidateTests {
    private final FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage()));
    private final UserController userController = new UserController(new UserService(new InMemoryUserStorage()));

    @Test
    public void testFilmValidAdding() throws ValidationException {
        Film film = new Film(12, "Film1", "Валидный фильм",
                LocalDate.of(2023, 1, 20), 22);
        assertEquals(film, filmController.addFilm(film));
    }

    @Test
    public void testFilmWrongReleaseDate() {
        Film film = new Film(2, "Film2", "Фильм с невалидной датой релиза",
                LocalDate.of(1895, 12, 20), 60);
        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void testUserValidAdding() throws ValidationException {
        User user = new User(1, "qwerty@yandex.ru", "qwerty", "Name",
                LocalDate.of(1997, 10, 20));
        assertEquals(user, userController.createUser(user));
    }

    @Test
    public void testUserWithEmptyName() throws ValidationException {
        User user = new User(33, "asdf@yandex.ru", "asdf", "",
                LocalDate.of(1997, 20, 10));
        userController.createUser(user);
        assertEquals(user.getName(), user.getLogin());
    }
}