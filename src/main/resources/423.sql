select student.name, student.age, faculty.name from student inner join faculty on faculty_id=faculty.id;
select student.name from avatar inner join student on avatar.student_id = student.id;