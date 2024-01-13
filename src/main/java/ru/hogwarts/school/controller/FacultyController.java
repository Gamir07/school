package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    @GetMapping("/getFacultiesByColor")
    public ResponseEntity<Collection<Faculty>> getAllFacultiesByColor(@RequestParam String color){
        if (service.getAllFacultiesByColor(color).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.getAllFacultiesByColor(color));
    }

    @GetMapping
    public Collection<Faculty> getListOfFaculties() {
        return service.getAllFaculties();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        service.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}
