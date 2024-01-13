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
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @InjectMocks
    FacultyServiceImpl out;
    @Mock
    FacultyRepository repository;
    Faculty FACULTY1 = new Faculty(1L,"Gryffindor", "red");
    Faculty FACULTY2 = new Faculty(2L,"Hufflepuff", "yellow");
    Faculty FACULTY3 = new Faculty(3L,"Slytherin", "green");
    Faculty FACULTY4 = new Faculty(4L,"Ravenclaw", "blue");

    List<Faculty> faculties;

    @BeforeEach
    void setUp() {
        faculties = new ArrayList<>(List.of(FACULTY1,FACULTY2,FACULTY3,FACULTY4));
    }

    @Test
    void shouldAddOrUpdateFacultySuccessfully() {
        Mockito.when(repository.save(FACULTY1)).thenReturn(FACULTY1);
        out.addFaculty(FACULTY1);
        Mockito.verify(repository, Mockito.times(1)).save(FACULTY1);
        assertEquals(FACULTY1, out.addFaculty(FACULTY1));
    }

    @Test
    void findFacultyById() {
        long ID = 3L;
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(FACULTY3));
        assertEquals(FACULTY3, out.getFaculty(FACULTY3.getId()));
        Mockito.verify(repository, Mockito.times(1)).findById(ID);
    }

    @Test
    void shouldThrowNotFoundException() {
        long ID = 7L;
        Mockito.when(repository.findById(ID)).thenThrow(HttpClientErrorException.NotFound.class);
        assertThrows((HttpClientErrorException.NotFound.class), () -> out.getFaculty(ID));
    }

    @Test
    void shouldSuccessfullyDeleteFaculty() {
        long ID = 4L;
        Mockito.doNothing().when(repository).deleteById(ID);
        out.deleteFaculty(ID);
        Mockito.verify(repository,Mockito.times(1)).deleteById(ID);
    }

    @Test
    void getAllFacultiesByColor() {
        List<Faculty> expectedList = List.of(FACULTY3);
        String COLOR = "green";
        Mockito.when(repository.findByColor(COLOR)).thenReturn(expectedList);
        assertIterableEquals(expectedList,out.getAllFacultiesByColor(COLOR));
        Mockito.verify(repository, Mockito.times(1)).findByColor(COLOR);

    }

    @Test
    void getAllFaculties() {
        Mockito.when(repository.findAll()).thenReturn(faculties);
        assertEquals(faculties,out.getAllFaculties());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }
}