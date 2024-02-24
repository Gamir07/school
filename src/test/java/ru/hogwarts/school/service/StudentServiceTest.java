package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import ru.hogwarts.school.model.Faculty;
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
    Faculty FACULTY1 = new Faculty(1L,"Gryffindor", "red");
    Faculty FACULTY2 = new Faculty(2L,"Hufflepuff", "yellow");
    Faculty FACULTY3 = new Faculty(3L,"Slytherin", "green");
    Faculty FACULTY4 = new Faculty(4L,"Ravenclaw", "blue");
    Student STUDENT1 = new Student(1L,"Andrew", 41,FACULTY1);
    Student STUDENT2 = new Student(2L,"Michael", 26,FACULTY2);
    Student STUDENT3 = new Student(3L,"Ana", 33,FACULTY1);
    Student STUDENT4 = new Student(4L,"Maria", 26,FACULTY4);

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
    void shouldFindAllStudentsByAge() {
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
    @Test
    void shouldFindStudentsByAgeBetween(){
        int MIN = 25;
        int MAX = 35;
        List<Student> expectedList = List.of(STUDENT2,STUDENT3,STUDENT4);
        Mockito.when(repository.findByAgeBetween(MIN,MAX)).thenReturn(expectedList);
        assertIterableEquals(expectedList, out.getStudentsByAgeBetween(MIN,MAX));
        Mockito.verify(repository,Mockito.times(1)).findByAgeBetween(MIN,MAX);
    }
    @Test
    void shouldFindStudentsByFacultyName(){
        String NAME = "Slytherin";
        List<Student> expectedList = List.of(STUDENT3);
        Mockito.when(repository.findStudentsByFaculty(NAME)).thenReturn(expectedList);
        assertIterableEquals(expectedList, out.getStudentsByFaculty(NAME));
        Mockito.verify(repository, Mockito.times(1)).findStudentsByFaculty(NAME);
    }

    @Test
    void shouldFindStudentsWithFirstLetterInName(){
        char letter = 'm';
        List<String> expectedList = List.of("Maria","Michael");
        Mockito.when(repository.findAll()).thenReturn(studentList);
        assertIterableEquals(expectedList, out.getStudentsWithFirstLetterInName(letter));
    }

    @Test
    void shouldFindAverageAgeOfAllStudentsUsingStreams(){
        int totalAge = STUDENT1.getAge() + STUDENT2.getAge()+STUDENT3.getAge()+ STUDENT4.getAge();
        double averageAge = (double) (totalAge)/studentList.size();
        Mockito.when(repository.findAll()).thenReturn(studentList);
        assertEquals(averageAge,out.getAverageAgeOfAllStudentsUsingStreams());
    }

}