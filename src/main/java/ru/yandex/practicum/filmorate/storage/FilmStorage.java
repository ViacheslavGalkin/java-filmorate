package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;


public interface FilmStorage {
    Film addFilm(Film film);

    Optional<Film> getFilmById(Long id);

    List<Film> getAllFilms();

    Film updateFilm(Film film);

    Film removeFilm(Film film);
}
