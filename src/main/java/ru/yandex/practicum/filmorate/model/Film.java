package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Film {

    private long id;
    @NotBlank
    private String name;
    @Size(max = 200)
    @NotBlank
    private String description;
    private LocalDate releaseDate;
    @NotNull
    @Positive
    private Integer duration;
    @JsonIgnore
    final Set<Long> likes = new HashSet<>();

}
