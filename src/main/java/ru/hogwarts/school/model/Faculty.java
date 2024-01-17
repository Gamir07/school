package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Faculty {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String color;

    public Faculty() {
    }

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    @OneToMany(mappedBy = "faculty")
    @JsonIgnore
    private Collection<Student> studentList;
}
