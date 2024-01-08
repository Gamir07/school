package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private Long counter = 0L;

    public Student addStudent(Student student) {
        student.setId(++counter);
        return students.put(counter, student);

    }

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        if (students.containsKey(student.getId())) {
            return students.put(student.getId(), student);
        }
        return null;
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    public Collection<Student> getAllStudentsByAge(int age) {
        return getAllStudents().stream().filter((student) -> age == student.getAge()).collect(Collectors.toList());
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }


}
