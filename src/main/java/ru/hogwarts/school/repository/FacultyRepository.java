package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findByColorIgnoreCase(String color);

    Faculty findByNameIgnoreCase(String name);

    @Query(value = "select faculty.* from student, faculty where faculty_id = faculty.id and student.id = :id", nativeQuery = true)
    Faculty findFacultyByStudentId(Long id);
}
