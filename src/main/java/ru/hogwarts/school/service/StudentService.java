package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student addStudent(Student student);
    Student getStudent(Long id);
    Student updateStudent(Student student);
    void deleteStudent(Long id);
    Collection<Student> getAllStudentsByAge(int age);
    Collection<Student> getAllStudents();
}
