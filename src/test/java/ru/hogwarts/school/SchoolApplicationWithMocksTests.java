package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class SchoolApplicationWithMocksTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository repository;
    @SpyBean
    private FacultyServiceImpl service;
    @InjectMocks
    private FacultyController controller;

    private Faculty faculty;
    private Faculty faculty2;
    private Faculty faculty3;
    private Student student;
    private List<Faculty> facultyList;

    @BeforeEach
    public void setUp() {
        faculty = new Faculty(1L, "Slytherin", "green");
        faculty2 = new Faculty(2L, "Gryffindor", "red");
        faculty3 = new Faculty(3L, "Ravenclaw", "blue");
        student = new Student(5L, "Harry Potter", 16, faculty);
        facultyList = List.of(faculty, faculty2, faculty3);

    }

    @Test
    public void saveFacultyTest() throws Exception {

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", faculty.getName());
        facultyObject.put("color", faculty.getColor());

        when(repository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.
                        post("/faculties")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }
    @Test
    public void putFacultyTest() throws Exception {

        Faculty faculty = new Faculty(1L, "Slytherin", "black");

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", faculty.getName());
        facultyObject.put("color", faculty.getColor());

        when(repository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.
                        post("/faculties")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }

    @Test
    public void getFacultyByIdTest() throws Exception {
        final long id = 1L;

        when(repository.findById(eq(id))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/ " + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void shouldThrowFacultyNotFoundException() throws Exception {
        final long id = 5L;

        when(repository.findById(eq(id))).thenThrow(FacultyNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFacultyByIdTest() throws Exception {

        final long id = 1L;

        doNothing().when(repository).deleteById(id);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculties/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void getFacultyByStudentIdTest() throws Exception {

        final long id = 2L;

        when(repository.findFacultyByStudentId((eq(id)))).thenReturn(faculty2);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/getFacultyByStudentId/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty2.getId()))
                .andExpect(jsonPath("$.name").value(faculty2.getName()))
                .andExpect(jsonPath("$.color").value(faculty2.getColor()));
    }

    @Test
    public void getFacultyByColorOrNameTest() throws Exception {

        when(repository.findByNameIgnoreCase("Slytherin")).thenReturn(faculty);
        when(repository.findByColorIgnoreCase("blue")).thenReturn(faculty3);
        when(repository.findAll()).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties")
                        .param("color", "blue")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty3.getId()))
                .andExpect(jsonPath("$.name").value(faculty3.getName()))
                .andExpect(jsonPath("$.color").value(faculty3.getColor()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties")
                        .param("name", "Slytherin")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
//                .andExpect(jsonPath("$.containsAll(facultyList)").value(true));
    }

}
