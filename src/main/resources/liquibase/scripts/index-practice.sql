--liquibase formatted sql

-- changeset ggainullin:1
CREATE INDEX student_name_index on student(name);

-- changeset ggainullin:2
CREATE INDEX faculty_name_color_index on faculty(name, color);