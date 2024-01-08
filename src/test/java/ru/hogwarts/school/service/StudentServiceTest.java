package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentService service;
    Map<Long, Student> students = new HashMap<>();

    @BeforeEach
    void setUp() {
        service = new StudentService();

    }

    static Stream<Arguments> argumentsStream() {
        return Stream.of(Arguments.of(new Student("Mark", 23)),
                Arguments.of(new Student("Mary", 19)),
                Arguments.of(new Student("John", 29)),
                Arguments.of(new Student("Helen", 26)));
    }

    @ParameterizedTest
    @MethodSource("argumentsStream")
    void shouldSuccessfullyAddStudent(Student student) {
        Student addedStudent = service.addStudent(student);

//        assertTrue(service.getAllStudents().contains(addedStudent));

    }

    @ParameterizedTest
    @ValueSource(longs = {2})
    void shouldReturnStudentById(Long id) {
        service.addStudent(new Student("Mary", 19));
        service.addStudent(new Student("Jessica", 27));
        service.addStudent(new Student("Alex", 31));
        assertEquals(new Student("Jessica", 27), service.getStudent(id));
    }

    @Test
    void shouldSuccessfullyUpdateStudent() {
    }

    @Test
    void shouldSuccessfullyDeleteStudent() {
    }

    @Test
    void getAllStudentsByAge() {
    }

    @Test
    void getAllStudents() {
    }
}