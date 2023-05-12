package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private long id = 1;

    @Override
    public User addUser(User user) {
        user.setId(id);
        users.put(id, user);
        id++;
        log.info("Будет сохранен пользователь: {}", user);

        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        log.info("Вернуть пользователя с идентификатором {}", id);
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Возвращено {} пользователей", users.values().size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User updateUser(User user) {
        long id = user.getId();
        if (users.containsKey(id)) {
            users.replace(id, user);
            log.info("Обновлен пользователь с идентификатором {}", id);
        } else {
            log.debug("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }

        return user;
    }

    @Override
    public User removeUser(User user) {
        long id = user.getId();
        if (users.containsKey(id)) {
            users.remove(id);
            log.info("Удален пользователь с идентификатором {}", id);
        } else {
            log.debug("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }

        return user;
    }

}
