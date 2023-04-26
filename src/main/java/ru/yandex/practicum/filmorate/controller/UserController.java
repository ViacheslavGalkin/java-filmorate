package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@Data
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    @PostMapping
    public User addUser(@RequestBody @Valid User user) throws ValidationException {
        if (isUserValid(user)) {
            user.setId(id++);
            log.debug("Будет сохранен пользователь: {}", user);
            users.put(user.getId(), user);
        }

        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) throws ValidationException {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь не найден!");
        } else {
            if (isUserValid(user)) {
                log.debug("Будет обновлен пользователь: {}", user);
                users.put(user.getId(), user);
            }
        }

        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    private Boolean isUserValid(User user) throws ValidationException {
        Boolean isValid = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            isValid = false;
            ValidationException exception = new ValidationException("Электронная почта пустая или не содержит @!");
            log.debug("Ошибка валидации!", exception);
            throw exception;
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            isValid = false;
            ValidationException exception = new ValidationException("Логин пустой или содержит пробелы!");
            log.debug("Ошибка валидации!", exception);
            throw exception;
        }
        if (LocalDate.parse(user.getBirthday(), formatter).isAfter(LocalDate.now())) {
            isValid = false;
            ValidationException exception = new ValidationException("Дата рождения пользователя в будущем!");
            log.debug("Ошибка валидации!", exception);
            throw exception;
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("Вместо пустого имени пользователя используется логин.");
        }

        return isValid;
    }

}
