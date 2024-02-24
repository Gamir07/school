package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(service.addFaculty(faculty));
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {

        return ResponseEntity.ok(service.updateFaculty(faculty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = service.getFaculty(id);
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        service.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getFacultyByColorOrName(@RequestParam(required = false) String color,
                                                  @RequestParam(required = false) String name) {
        if (color != null && !color.isBlank()) {
            Faculty facultyByColor = service.getFacultyByColor(color);
            return ResponseEntity.ok(facultyByColor);
        } else if (name != null && !name.isBlank()) {
            Faculty facultyByName = service.getFacultyByName(name);
            return ResponseEntity.ok(facultyByName);
        } else {
            return ResponseEntity.ok(service.getAllFaculties());
        }
    }
    @GetMapping("/getFacultyByStudentId/{id}")
    public Faculty getFacultyByStudentId(@PathVariable Long id){
        return service.getFacultyByStudentId(id);
    }

    @GetMapping("/getTheLongestFacultyName")
    public ResponseEntity<String> getTheLongestFacultyName(){
        return ResponseEntity.ok(service.getTheLongestFacultyName());
    }
}
