package me.zhukov.hogwarts.repository;

import me.zhukov.hogwarts.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorOrName(String colorOrName);
}
