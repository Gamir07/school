package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class SchoolApplicationWithMocksForAvatarServiceTests {

    @Mock
    private AvatarRepository repository;
    @Mock
    private StudentServiceImpl studentService;
    @InjectMocks
    private AvatarServiceImpl avatarService;

    Student student1;
    Student student2;
    Student student3;
    Student student4;
    Avatar avatar1;
    Avatar avatar2;
    Avatar avatar3;
    Avatar avatar4;

    @BeforeEach
    void setUp() {
        student1 = new Student();
        student1.setId(1L);
        student1.setName("Robert Walker");
        student1.setAge(19);
        student2 = new Student();
        student2.setId(2L);
        student2.setName("Miranda Otto");
        student2.setAge(34);
        student3 = new Student();
        student3.setId(3L);
        student3.setName("Jeremy Mason");
        student3.setAge(26);
        student4 = new Student();
        student4.setId(4L);
        student4.setName("David Beckham");
        student4.setAge(24);
        avatar1 = new Avatar();
        avatar1.setId(1L);
        avatar1.setStudent(student1);
        avatar2 = new Avatar();
        avatar2.setId(2L);
        avatar2.setStudent(student2);
        avatar3 = new Avatar();
        avatar3.setId(3L);
        avatar3.setStudent(student3);
        avatar4 = new Avatar();
        avatar4.setId(4L);
        avatar4.setStudent(student4);

    }


    @Test
    public void uploadAvatarTest() throws Exception{

//        when(repository.save(avatar3)).thenReturn(avatar3);
//        assertThat(avatarService.uploadAvatar(3L, any(MultipartFile.class)));
//        verify(studentService,times(1)).getStudent(3L);



    }

    @Test
    public void findAllTest() {

        List<Avatar> avatarList = List.of(avatar1, avatar2, avatar3);
        final int page = 1;
        final int size = 3;
        PageRequest pageRequest = PageRequest.of(page, size);

        when(repository.findAll(pageRequest).getContent()).thenReturn(avatarList);
        assertIterableEquals(avatarService.findAll(page, size), avatarList);

    }

    @Test
    public void findAvatar()  {
        when(repository.findByStudentId(2L))
                .thenReturn(Optional.of(avatar2));
        assertEquals(avatarService.findAvatar(2L), avatar2);

    }

}
