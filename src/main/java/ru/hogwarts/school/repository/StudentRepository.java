package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    @Query(value = "select student.* from student, faculty where faculty_id = faculty.id and faculty.name= :name", nativeQuery = true)
    List<Student> findStudentsByFaculty(String name);

    @Query(value = "select count(*) as number_of_students from student", nativeQuery = true)
    Integer getNumberOfStudents();

    @Query(value = "select avg(age) as average_age from student", nativeQuery = true)
    Integer getAverageAgeOfAllStudents();

    @Query(value = "select * from student offset 5", nativeQuery = true)
    List<Student> getLastFiveStudents();

}
