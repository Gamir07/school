package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student addStudent(Student student);
    Student getStudent(Long id);
    Student updateStudent(Student student);
    void deleteStudent(Long id);
    Collection<Student> getAllStudentsByAge(int age);
    Collection<Student> getAllStudents();
    Collection<Student> getStudentsByAgeBetween(int min, int max);
    Collection<Student> getStudentsByFaculty(String  name);
}
