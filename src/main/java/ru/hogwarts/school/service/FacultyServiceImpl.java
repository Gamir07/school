package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for add faculty {}", faculty);
        return repository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        Optional<Faculty> faculty = repository.findById(id);
        if (faculty.isEmpty()) {
            logger.error("There is no faculty by id = {}", id);
            throw new FacultyNotFoundException();
        } else {
            logger.info("Was invoked method for getFaculty by id = {}",id);
            return faculty.get();
        }
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty = {}", faculty);
        return repository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty by id = {}", id);
        repository.deleteById(id);
    }

    public Faculty getFacultyByColor(String color) {
        logger.info("Was invoked method for getFacultyByColor = {}", color);
        return repository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for getAllFaculties");
        return repository.findAll();
    }

    public Faculty getFacultyByName(String name) {
        logger.info("Was invoked method for getFacultyByName = {}", name);
        return repository.findByNameIgnoreCase(name);
    }
    public Faculty getFacultyByStudentId(Long id){
        logger.info("Was invoked method for getFacultyByStudentId = {}", id);
       return repository.findFacultyByStudentId(id);
    }
    public String getTheLongestFacultyName(){
        logger.info("Was invoked method for getTheLongestFacultyName");
        return repository.findAll()
                .stream()
                .max(Comparator.comparing(faculty -> faculty.getName().length())).get().getName();
    }

}
