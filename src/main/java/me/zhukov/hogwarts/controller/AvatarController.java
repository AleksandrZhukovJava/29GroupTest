package me.zhukov.hogwarts.controller;

import me.zhukov.hogwarts.model.Avatar;
import me.zhukov.hogwarts.model.dto.AvatarDto;
import me.zhukov.hogwarts.service.AvatarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/avatar")
@RestController
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public long uploadAvatar(@RequestParam long studentId, @RequestBody MultipartFile multipartFile) throws IOException {
        return avatarService.addAvatar(studentId, multipartFile);
    }

    @GetMapping(value = "/get/from-db")
    public ResponseEntity<byte[]> getAvatarFromDb(@RequestParam long studentId) {
        Avatar avatar = avatarService.getAvatarFromDb(studentId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .body(avatar.getData());
    }

    @GetMapping(value = "/get/from-local")
    public ResponseEntity<byte[]> getAvatarFromLocal(@RequestParam long studentId) throws IOException {
        AvatarDto dto = avatarService.getAvatarFromLocal(studentId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(dto.getMediaType())
                .body(dto.getData());
    }
}
