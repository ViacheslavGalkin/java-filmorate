# java-filmorate

## SQL diagram
![diogram](src/main/resources/DB%20diagram.png)

## Описание базы данных для проекта

### База данных состоит из 7 таблиц:

#### Таблица film содержит данные о фильме (PK - film_id, FK - rating_id):

+ film_id - идентификатор фильма ;
+ name - название фильма;
+ description - краткое описание фильма(не более 200 символов);
+ release_date - дата выхода фильма;
+ duration - продолжительность фильма;
+ rating_id - внешний ключ, идентификатор рейтинга, связь с таблицей rating;

#### Таблица rating содержит данные о возрастных рейтингах (PK - rating_id):

+ rating_id - идентификатор рейтинга;
+ name - названия рейтинга;
+ description - описание рейтинга;

#### Таблица film_genre содержит информацию о жанрах фильма (PK - film_id, FK - genre_id):

+ film_id - идентификатор фильма; 
+ genre_id - идентификатор жанра, связь с таблицей genre;

#### Таблица genre содержит информацию о всех жанрах (PK - genre_id):

+ genre_id - тдентификатор жанра; 
+ name - название жанра.

#### Таблица user содержит данные о пользователе (PK - user_id):

+ user_id - идентификатор пользователя;
+ name - имя пользователя;
+ login - логин пользователя;
+ email - электронная почта пользователя;
+ birthday - дата рождения пользователя;

#### Таблица friendship содержит информацию о дружбе пользователей (PK - user_id, friend_id):

+ user_id - идентификатор пользователя;
+ friend_id - идентификатор пользователя(пользователь добавленный в друзья);
+ status - статус дружбы пользователей, имеет 2 значения:

    + неподтверждённая - false (первый пользователь отправил запрос на добавление второго в друзья):
    + подтверждённая - true (второй пользователь принял запрос);

#### Таблица likes содержит данные о понравившихся пользователю фильмах (PK - user_id, film_id):

+ film_id - идентификатор фильма, которому был поставлен like;
+ user_id - идентификатор пользователя, который поставил like;


### Примеры запросов к БД:

#### Посчитать количество пользователей, которым понравился фильм "Дюна":
~~~ roomsql
SELECT COUNT(u.user_id)
FROM user AS u
LEFT OUTER JOIN like AS l ON l.user_id = u.user_id
INNER JOIN film AS f ON l.film_id = f.film_id
WHERE f.name = "Дюна";
~~~

#### Вывусти названия всех фильмов с определённым жанром и рейтингом:
~~~ roomsql
SELECT f.name
FROM film AS f
INNER JOIN rating AS r ON r.rating_id = f.rating_id
INNER JOIN film_genre AS fg ON fg.film_id = f.film_id
INNER JOIN genre AS g ON g.genre_id = fg.genre_id
WHERE r.name = "Требуемый рейтинг"
      AND g.name = "Требуемый жанр";
~~~

