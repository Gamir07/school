package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

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
    public ResponseEntity<Collection<Student>> getStudentsByAge(@PathVariable int age){
        if (service.getAllStudentsByAge(age).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.getAllStudentsByAge(age));
    }

    @GetMapping
    public Collection<Student> getAllStudents() {
        return service.getAllStudents();
    }
}
