package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(service.addStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(service.updateStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = service.getStudent(id);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/getStudentsByAge/{age}")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@PathVariable int age) {
        if (service.getAllStudentsByAge(age).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.getAllStudentsByAge(age));
    }

    @GetMapping("/getStudentsByAgeBetween")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam int min,
                                                                       @RequestParam int max) {
        Collection<Student> studentsByAgeBetween = service.getStudentsByAgeBetween(min, max);
        return ResponseEntity.ok(studentsByAgeBetween);
    }

    @GetMapping
    public Collection<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/getStudentsByFaculty")
    public ResponseEntity<Collection<Student>> getStudentsByFaculty(@RequestParam String name) {
        if (service.getStudentsByFaculty(name).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.getStudentsByFaculty(name));
    }

    @GetMapping("/getNumberOfStudents")
    public ResponseEntity<Integer> getNumberOfStudents() {
        return ResponseEntity.ok(service.getNumberOfStudents());
    }

    @GetMapping("/getAverageAgeOfAllStudents")
    public ResponseEntity<Integer> getAverageAgeOfAllStudents() {
        return ResponseEntity.ok(service.getAverageAgeOfAllStudents());
    }

    @GetMapping("/getLastFiveStudents")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(service.getLastFiveStudents());
    }

    @GetMapping("/getStudentsWithFirstLetterInName/{letter}")
    public ResponseEntity<Collection<String>> getStudentsWithFirstLetterInName(@PathVariable Character letter) {
        return ResponseEntity.ok(service.getStudentsWithFirstLetterInName(letter));
    }

    @GetMapping("/getAverageAgeOfAllStudentsUsingStreams")
    public ResponseEntity<Integer> getAverageAgeOfAllStudentsUsingStreams(){
        Double average = service.getAverageAgeOfAllStudentsUsingStreams();
        return ResponseEntity.ok((average.intValue()));
    }
}
