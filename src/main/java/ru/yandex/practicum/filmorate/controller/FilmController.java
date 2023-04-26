package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        setFilmId(film);
        if (isFilmValid(film)) {
            log.debug("Будет добавлен фильм: {}", film);
            films.put(film.getId(), film);
        }

        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильм не найден!");
        } else {
            if (isFilmValid(film)) {
                log.debug("Будет обновлен фильм: {}", film);
                films.put(film.getId(), film);
            }
        }

        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    private Boolean isFilmValid(Film film) throws ValidationException {
        Boolean isValid = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (film.getName() == null || film.getName().isBlank()) {
            isValid = false;
            ValidationException exception = new ValidationException("Пустое название фильма!");
            log.debug("Ошибка валидации!", exception);
            throw exception;
        }
        if (film.getDescription().length() > 200) {
            isValid = false;
            ValidationException exception = new ValidationException("Длина описания превышает 200 символов!");
            log.debug("Ошибка валидации!", exception);
            throw exception;
        }
        if (LocalDate.parse(film.getReleaseDate(), formatter).isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            isValid = false;
            ValidationException exception = new ValidationException("Дата релиза ранее 28 декабря 1895 года!");
            log.debug("Ошибка валидации!", exception);
            throw exception;
        }
        if (film.getDuration() <= 0) {
            isValid = false;
            ValidationException exception = new ValidationException("Отрицательная продолжительность фильма!");
            log.debug("Ошибка валидации!", exception);
            throw exception;
        }

        return isValid;
    }

    private void setFilmId(Film film) {
        film.setId(id++);
    }

}
