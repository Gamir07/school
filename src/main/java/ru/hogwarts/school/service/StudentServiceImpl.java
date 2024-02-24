package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method for create student {}", student);
        return repository.save(student);
    }

    public Student getStudent(Long id) {
        Optional<Student> student = repository.findById(id);
        if (student.isEmpty()) {
            logger.error("There is no student by id {}", id);
            throw new StudentNotFoundException();
        } else {
            logger.info("Was invoked method for create student");
            return student.get();
        }
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student {}", student);
        return repository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student by id {}", id);
        repository.deleteById(id);
    }

    public Collection<Student> getAllStudentsByAge(int age) {
        logger.info("Was invoked method for getAllStudentsByAge {}", age);
        return repository.findByAge(age);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for getAllStudents");
        return repository.findAll();
    }

    public Collection<Student> getStudentsByAgeBetween(int min, int max) {
        logger.info("Was invoked method for getStudentsByAgeBetween {} and {}", min, max);
        return repository.findByAgeBetween(min, max);
    }

    public Collection<Student> getStudentsByFaculty(String name) {
        logger.info("Was invoked method for getStudentsByFaculty {}", name);
        return repository.findStudentsByFaculty(name);
    }

    @Override
    public Integer getNumberOfStudents() {
        logger.info("Was invoked method for getNumberOfStudents");
        return repository.getNumberOfStudents();
    }

    @Override
    public Integer getAverageAgeOfAllStudents() {
        logger.info("Was invoked method for getAverageAgeOfAllStudents");
        return repository.getAverageAgeOfAllStudents();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for getLastFiveStudents");
        return repository.getLastFiveStudents();
    }

    @Override
    public List<String> getStudentsWithFirstLetterInName(Character letter) {
        logger.info("Was invoked method for getStudentsWithFirstLetterInName = {}", letter);
        return repository.findAll()
                .stream()
                .filter(st -> st.getName().startsWith(letter.toString().toUpperCase()))
                .sorted(Comparator.comparing(st->st.getName().toUpperCase()))
                .map(Student::getName)
                .toList();
    }

    @Override
    public Double getAverageAgeOfAllStudentsUsingStreams() {
        logger.info("Was invoked method for getAverageAgeOfAllStudentsUsingStreams");
        return repository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average().getAsDouble();
    }
}
