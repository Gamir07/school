package ru.hogwarts.school;

import org.assertj.core.api.FactoryBasedNavigableIterableAssert;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController controller;
    @Autowired
    private StudentRepository repository;
    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testPostMethod() throws Exception {
        Student student = new Student();
        student.setName("Andrew Lincoln");
        student.setAge(34);

        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port + "/students", student, Student.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getName(), is("Andrew Lincoln"));
        assertThat(response.getBody().getAge(), is(34));

    }

    @Test
    void testPutMethod() throws Exception {
        Student student = new Student();
        student.setName("Andrew Lincoln");
        student.setAge(34);

        restTemplate.postForEntity("http://localhost:" + port + "/students", student, Student.class);
        student.setName("Helen Buffet");
        ResponseEntity<Student> exchange = restTemplate.exchange("http://localhost:" + port + "/students", HttpMethod.PUT, new HttpEntity<>(student), Student.class);
        assertThat(exchange.getStatusCode(), is(HttpStatus.OK));
        assertThat(exchange.getBody().getName(), is("Helen Buffet"));

    }

    @Test
    void testGetStudentByIdMethod() throws Exception {
        Student student = new Student();
        student.setName("Mark Webber");
        student.setAge(41);

        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port + "/students", student, Student.class);
        Long id = response.getBody().getId();
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/students" + id, Student.class)).isNotNull();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getName(), is(student.getName()));
        assertThat(response.getBody().getAge(), is(student.getAge()));

    }

    @Test
    void testDeleteMethod() {
        Student student = new Student();
        student.setName("Maria Wilson");
        student.setAge(25);

        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port + "/students", student, Student.class);
        Long id = response.getBody().getId();
        restTemplate.exchange("http://localhost:" + port + "/students" + id, HttpMethod.DELETE, null, Student.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void testGetStudentsByAgeMethod() throws Exception {
        Student student = new Student();
        student.setName("Mark Webber");
        student.setAge(41);
        Student student2 = new Student();
        student2.setName("John Andrews");
        student2.setAge(25);
        Student student3 = new Student();
        student3.setName("Ana Jordan");
        student3.setAge(41);

        int age = 25;
        restTemplate.postForEntity("http://localhost:" + port + "/students", student, Student.class);
        restTemplate.postForEntity("http://localhost:" + port + "/students", student2, Student.class);
        restTemplate.postForEntity("http://localhost:" + port + "/students", student3, Student.class);
        ResponseEntity<Collection<Student>> response = restTemplate.exchange("http://localhost:" + port
                + "/students/getStudentsByAge/{age}", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        }, age);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().size(), is(1));

    }

    @Test
    void testGetStudentByAgeBetweenMethod() throws Exception {
        Student student = new Student();
        student.setName("Mark Webber");
        student.setAge(30);
        Student student2 = new Student();
        student2.setName("John Andrews");
        student2.setAge(25);
        Student student3 = new Student();
        student3.setName("Ana Jordan");
        student3.setAge(29);

        restTemplate.postForEntity("http://localhost:" + port + "/students", student, Student.class);
        restTemplate.postForEntity("http://localhost:" + port + "/students", student2, Student.class);
        restTemplate.postForEntity("http://localhost:" + port + "/students", student3, Student.class);
        ResponseEntity<Collection<Student>> response = restTemplate.exchange("http://localhost:" + port
                + "/students/getStudentsByAgeBetween?min= " + 25 + "&max=" + 29, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().size(), is(2));

    }

    @Test
    void testGetStudentsByFacultyMethod() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("IT");
        faculty.setColor("red");
        Faculty faculty1 = new Faculty();
        faculty1.setName("HR");
        faculty1.setColor("green");
        assertThat(restTemplate.postForEntity("http://localhost:" + port + "/faculties", faculty, Faculty.class).getStatusCode(),is(HttpStatus.OK));
        assertThat(restTemplate.postForEntity("http://localhost:" + port + "/faculties", faculty1, Faculty.class).getStatusCode(),is(HttpStatus.OK));

        Student student = new Student();
        student.setName("Mark Webber");
        student.setAge(30);
        student.setFaculty(faculty1);
        Student student2 = new Student();
        student2.setName("John Andrews");
        student2.setAge(25);
        student2.setFaculty(faculty1);
        Student student3 = new Student();
        student3.setName("Ana Jordan");
        student3.setAge(29);
        student3.setFaculty(faculty);

        String facultyName = "IT";

        restTemplate.postForEntity("http://localhost:" + port + "/students", student, Student.class);
        restTemplate.postForEntity("http://localhost:" + port + "/students", student2, Student.class);
        restTemplate.postForEntity("http://localhost:" + port + "/students", student3, Student.class);
        ResponseEntity<Collection<Student>> response = restTemplate.exchange("http://localhost:" + port
                + "/students/getStudentsByFaculty?name= " + facultyName, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().size(), is(1));

    }
    @Test
    void testGetAllStudentsMethod() throws Exception {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/students", String.class)).isNotNull();
    }

}
