package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);
    Faculty getFaculty(Long id);
    Faculty updateFaculty(Faculty faculty);
    void deleteFaculty(Long id);
    Faculty getFacultyByColor(String color);
    Faculty getFacultyByName(String name);
    Collection<Faculty> getAllFaculties();
    Faculty getFacultyByStudentId(Long id);
    String getTheLongestFacultyName();

}
