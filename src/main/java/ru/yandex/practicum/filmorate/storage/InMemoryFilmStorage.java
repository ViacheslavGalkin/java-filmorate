package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private HashMap<Long, Film> films = new HashMap<>();
    private long id = 1;

    private void validateReleaseDate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            ValidationException exception = new ValidationException("Дата релиза ранее 28 декабря 1895 года!");
            log.debug("Ошибка валидации!", exception);
            throw exception;
        }
    }

    @Override
    public Film addFilm(Film film) {
        validateReleaseDate(film);
        film.setId(id);
        films.put(film.getId(), film);
        id++;
        log.debug("Будет добавлен фильм: {}", film);

        return film;
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        log.info("Вернуть фильм с идентификатором {}", id);
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> getAllFilms() {
        log.info("Возвращено {} фильмов", films.values().size());
        return new ArrayList<>(films.values());
    }

    @Override
    public Film updateFilm(Film film) {
        long id = film.getId();
        if (films.containsKey(id)) {
            films.put(id, film);
            log.info("Обновлен фильм с идентификатором {}", film.getId());
        } else {
            log.debug("Фильм не найден");
            throw new NotFoundException("Фильм не найден");
        }

        return film;
    }

    @Override
    public Film removeFilm(Film film) {
        long id = film.getId();
        if (films.containsKey(id)) {
            films.remove(id);
            log.info("Удален фильм с идентификатором {}", film.getId());
        } else {
            log.debug("Фильм не найден");
            throw new NotFoundException("Фильм не найден");
        }

        return film;
    }

}
