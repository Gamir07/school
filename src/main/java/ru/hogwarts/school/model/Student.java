package ru.hogwarts.school.model;

import lombok.*;

@NoArgsConstructor
@Data
public class Student {
    private Long id;
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
