package ru.hogwarts.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;

}
