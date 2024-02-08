package ru.hogwarts.school.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

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

    public Collection<Student> getStudentsByAgeBetween(int min, int max){
        return repository.findByAgeBetween(min, max);
    }
    public Collection<Student> getStudentsByFaculty(String name){
        return repository.findStudentsByFaculty(name);
    }

    @Override
    public Integer getNumberOfStudents() {
        return repository.getNumberOfStudents();
    }

    @Override
    public Integer getAverageAgeOfAllStudents() {
        return repository.getAverageAgeOfAllStudents();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return repository.getLastFiveStudents();
    }


}
