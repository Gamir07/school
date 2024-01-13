package ru.hogwarts.school.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService{

    private FacultyRepository repository;

    public Faculty addFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        return repository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        repository.deleteById(id);
    }

    public Collection<Faculty> getAllFacultiesByColor(String color) {
        return repository.findByColor(color);
    }

    public Collection<Faculty> getAllFaculties() {
        return repository.findAll();
    }

}
