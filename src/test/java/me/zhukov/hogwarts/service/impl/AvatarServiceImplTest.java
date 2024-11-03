package me.zhukov.hogwarts.service.impl;

import me.zhukov.hogwarts.model.Avatar;
import me.zhukov.hogwarts.model.Student;
import me.zhukov.hogwarts.repository.AvatarRepository;
import me.zhukov.hogwarts.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

class AvatarServiceImplTest {
    private static final Path TEST_PATH = Path.of("src/test/image");
    private final AvatarRepository avatarRepository = mock(AvatarRepository.class);
    private final StudentRepository studentRepository = mock(StudentRepository.class);
    private final AvatarServiceImpl avatarService = new AvatarServiceImpl(avatarRepository, studentRepository, TEST_PATH);

    @AfterEach

    public void setDown() throws IOException {
        Files.walk(TEST_PATH)
                .sorted(Comparator.reverseOrder())
                .forEach(x -> {
                    try {
                        Files.delete(x);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    void addAvatar() throws IOException {
        Student student = new Student("Oleg", 18);
        student.setId(1L);
        Avatar avatar = new Avatar();
        avatar.setId(1L);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(avatarRepository.findByStudentId(anyLong())).thenReturn(null);
        when(avatarRepository.save(any(Avatar.class))).thenReturn(avatar);

        MockMultipartFile multipartFile = new MockMultipartFile("file.jpg",
                "file.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[0]);

        // test
        long actual = avatarService.addAvatar(nextLong(1, 100L), multipartFile);

        //check
        assertThat(Files.walk(TEST_PATH)).hasSize(2);

    }

    @Test
    void getAvatarFromDb() {
    }

    @Test
    void getAvatarFromLocal() {
    }
}