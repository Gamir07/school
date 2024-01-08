package ru.hogwarts.school.model;

import lombok.*;

@NoArgsConstructor
@Data
public class Faculty {
    private Long id;
    private String name;
    private String color;

    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
