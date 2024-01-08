package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long counter = 0L;

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++counter);
        return faculties.put(counter, faculty);

    }

    public Faculty getFaculty(Long id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return faculties.put(faculty.getId(), faculty);
    }

    public Faculty deleteFaculty(Long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> getAllFacultiesByColor(String color) {
        return getAllFaculties().stream().filter((faculty) -> color.equals(faculty.getColor())).collect(Collectors.toList());
    }

    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

}
