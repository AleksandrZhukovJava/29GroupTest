package me.zhukov.hogwarts.repository;

import me.zhukov.hogwarts.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Avatar findByStudentId(long studentId);
}
