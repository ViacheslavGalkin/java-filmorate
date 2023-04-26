package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull
    private int id;
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @NotNull
    private String birthday;
}
