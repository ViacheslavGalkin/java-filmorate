package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film createFilm(Film film) {
        log.info("Создан фильм с идентификатором {}", film.getId());
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        log.info("Обновлен фильм с идентификатором {}", film.getId());
        return filmStorage.updateFilm(film);
    }

    public List<Film> getAllFilms() {
        log.info("Получено {} фильмов", filmStorage.getAllFilms().size());
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(long filmId) {
        Optional<Film> film = filmStorage.getFilmById(filmId);
        if (film.isPresent()) {
            log.info("Получен фильм с идентификатором {} ", filmId);
            return film.get();
        } else {
            throw new NotFoundException("Фильм с идентификатором " + filmId + " не найден");
        }
    }

    public Film addLikes(long filmId, long userId) {
        Film film = getFilmById(filmId);
        if (!film.getLikes().contains(userId)) {
            film.getLikes().add(userId);
            log.info("Добавлен лайк к фильму ", filmId);
            return film;
        } else {
            log.debug("Пользователь не найден");
            throw new UserAlreadyExistException("Пользователь не найден");
        }
    }

    public Film removeLikes(long filmId, long userId) {
        if (filmId < 0 || userId < 0) {
            throw new NotFoundException("Идентификатор не может быть отрицательным");
        }
        Film film = getFilmById(filmId);
        film.getLikes().remove(userId);
        log.info("Пользователь {} удалил лайк у фильма {}", userId, filmId);
        return film;
    }

    public List<Film> favoritesFilms(Integer number) {
        return filmStorage.getAllFilms().stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(film -> film.getLikes().size())))
                .limit(number)
                .collect(Collectors.toList());
    }
}
