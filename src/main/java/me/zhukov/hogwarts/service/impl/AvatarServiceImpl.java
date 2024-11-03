package me.zhukov.hogwarts.service.impl;

import me.zhukov.hogwarts.exception.StudentNotFoundException;
import me.zhukov.hogwarts.model.Avatar;
import me.zhukov.hogwarts.model.Student;
import me.zhukov.hogwarts.model.dto.AvatarDto;
import me.zhukov.hogwarts.repository.AvatarRepository;
import me.zhukov.hogwarts.repository.StudentRepository;
import me.zhukov.hogwarts.service.AvatarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final Path pathDir;

    public AvatarServiceImpl(AvatarRepository avatarRepository,
                             StudentRepository studentRepository,
                             @Value("${image.path}") Path pathDir) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.pathDir = pathDir;
    }

    @Override
    public long addAvatar(long studentId, MultipartFile multipartFile) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        Path path = saveLocal(multipartFile);

        return saveBD(student, multipartFile, path);
    }

    @Override
    public Avatar getAvatarFromDb(long studentId) {
        return Optional.ofNullable(avatarRepository.findByStudentId(studentId))
                .orElseThrow(() -> new RuntimeException("avatar not found by student id"));
    }

    @Override
    public AvatarDto getAvatarFromLocal(long studentId) throws IOException {
        Avatar avatar = avatarRepository.findByStudentId(studentId);
        if (avatar == null) {
            throw new RuntimeException("аватар не найден");
        }

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(avatar.getFilePath()))) {
            return new AvatarDto(bufferedInputStream.readAllBytes(), MediaType.parseMediaType(avatar.getMediaType()));
        }
    }

    private Path saveLocal(MultipartFile multipartFile) throws IOException {
        createDirectoryIfNotExist();

        Path path = Path.of(pathDir.toString(), UUID.randomUUID() + getExtension(multipartFile));

        Files.write(path, multipartFile.getBytes());

        //Files.copy(multipartFile.getInputStream(), path);

//        Files.createFile(path);
//        try(BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(path.toFile()))){
//            fos.write(multipartFile.getBytes());
//        }

//        try (BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(multipartFile.getBytes()));
//             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path.toFile()))) {
//            bos.write(bis.readAllBytes());
//        }


        return path;
    }

    private String getExtension(MultipartFile multipartFile) {
        if (multipartFile.getOriginalFilename() == null){
            throw new RuntimeException("Не корректный формат файла");
        }
        return multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.'));
    }

    private void createDirectoryIfNotExist() throws IOException {
        if (Files.notExists(pathDir)) {
            Files.createDirectories(pathDir);
        }
    }

    private long saveBD(Student student, MultipartFile multipartFile, Path path) throws IOException {
        Avatar avatar = new Avatar(
                path.toString(),
                multipartFile.getSize(),
                multipartFile.getContentType(),
                multipartFile.getBytes(),
                student
        );

        Avatar savedAvatar = avatarRepository.findByStudentId(student.getId());
        if (savedAvatar != null) {
            Files.delete(Path.of(savedAvatar.getFilePath()));
            avatar.setId(savedAvatar.getId());
        }

        return avatarRepository.save(avatar).getId();
    }
}
