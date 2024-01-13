package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    StudentServiceImpl out;

    @Mock
    StudentRepository repository;

    Student STUDENT1 = new Student(1L,"Andrew", 41);
    Student STUDENT2 = new Student(2L,"Michael", 26);
    Student STUDENT3 = new Student(3L,"Ana", 33);
    Student STUDENT4 = new Student(4L,"Maria", 26);

    List<Student> studentList;

    @BeforeEach
    void setUp() {
        studentList = new ArrayList<>(List.of(STUDENT1, STUDENT2, STUDENT3, STUDENT4));
    }

    @Test
    void shouldAddOrUpdateStudentSuccessfully() {
        Mockito.when(repository.save(STUDENT1)).thenReturn(STUDENT1);
        out.addStudent(STUDENT1);
        Mockito.verify(repository, Mockito.times(1)).save(STUDENT1);
        assertEquals(STUDENT1, out.addStudent(STUDENT1));
    }

    @Test
    void findStudentById() {
        long ID = 2L;
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(STUDENT2));
        assertEquals(STUDENT2, out.getStudent(STUDENT2.getId()));
        Mockito.verify(repository, Mockito.times(1)).findById(ID);
    }
    @Test
    void shouldThrowNotFoundException() {
        long ID = 7L;
        Mockito.when(repository.findById(ID)).thenThrow(HttpClientErrorException.NotFound.class);
        assertThrows((HttpClientErrorException.NotFound.class), () -> out.getStudent(ID));
    }

    @Test
    void shouldSuccessfullyDeleteStudent() {
        long ID = 4L;
        Mockito.doNothing().when(repository).deleteById(ID);
        out.deleteStudent(ID);
        Mockito.verify(repository,Mockito.times(1)).deleteById(ID);
    }

    @Test
    void getAllStudentsByAge() {
        List<Student> expectedStudentList = List.of(STUDENT2, STUDENT4);
        int AGE = 26;
        Mockito.when(repository.findByAge(AGE)).thenReturn(expectedStudentList);
        assertIterableEquals(expectedStudentList, out.getAllStudentsByAge(AGE));
        Mockito.verify(repository, Mockito.times(1)).findByAge(AGE);
    }

    @Test
    void getAllStudents() {
        Mockito.when(repository.findAll()).thenReturn(studentList);
        assertEquals(studentList, out.getAllStudents());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }
}