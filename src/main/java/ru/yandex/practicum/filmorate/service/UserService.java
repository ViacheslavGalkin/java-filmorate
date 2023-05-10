package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        validateUserName(user);
        log.info("Создан пользователь с идентификатором {}", user.getId());
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        validateUserName(user);
        log.info("Обновлен пользователь с идентификатором {}", user.getId());
        return userStorage.updateUser(user);
    }

    public List<User> getAllUsers() {
        log.info("Получено {} пользователей", userStorage.getAllUsers().size());
        return userStorage.getAllUsers();
    }

    public User getUserById(long id) {
        log.info("Получение пользователя с идентификатором {}", id);
        Optional<User> user = userStorage.getUserById(id);
        if (user.isPresent()) {
            log.info("Получен пользователь с идентификатором {}", id);
            return user.get();
        } else {
            throw new NotFoundException("Пользователь с идентификатором " + id + " не найден");
        }
    }


    public void addFriend(Long id, Long friendId) {
        User user = getUserById(id);
        if (user.getFriends().contains(friendId)) {
            log.info("Пользователи с идентификаторами уже друзья {} {}", id, friendId);
            throw new ValidationException("Пользователь " + id + " и пользователь " + friendId +
                    "уже друзья");
        }
        User friend = getUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(id);

        log.info("Были добавлены друг другу в друзья ", id, friend);
    }

    public void removeFriendById(long id, long friendId) {
        User user = getUserById(id);
        log.info("Пользователь с идентификатором {}", friendId, " был удален из друзей пользователя ", id);
        user.getFriends().remove(friendId);
    }

    public List<User> getAllFriends(long id) {
        log.info("Получен список всех друзей");
        return getUserById(id).getFriends()
                .stream()
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(long userId, long friendId) {
        log.info("Получен список общих друзей");
        List<User> friends = getAllFriends(userId);
        friends.retainAll(getAllFriends(friendId));
        return friends;

    }

    public void validateUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
