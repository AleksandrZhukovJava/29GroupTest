package me.zhukov.hogwarts.service;

import me.zhukov.hogwarts.model.Avatar;
import me.zhukov.hogwarts.model.dto.AvatarDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {
    long addAvatar(long studentId, MultipartFile multipartFile) throws IOException;

    Avatar getAvatarFromDb(long studentId);

    AvatarDto getAvatarFromLocal(long studentId) throws IOException ;
}