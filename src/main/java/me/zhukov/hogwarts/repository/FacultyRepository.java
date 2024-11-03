package me.zhukov.hogwarts.repository;

import me.zhukov.hogwarts.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(@Param("color") String color, @Param("name") String name);
}

