package ru.hogwarts.school.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService{

    private StudentRepository repository;

    public Student addStudent(Student student) {
        return repository.save(student);
    }

    public Student getStudent(Long id) {
        return repository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    public Student updateStudent(Student student) {
        return repository.save(student);
    }

    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }

    public Collection<Student> getAllStudentsByAge(int age) {
        return repository.findByAge(age);
    }

    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }


}
