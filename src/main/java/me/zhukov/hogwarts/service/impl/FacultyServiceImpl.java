package me.zhukov.hogwarts.service.impl;

import jakarta.annotation.PostConstruct;
import me.zhukov.hogwarts.model.Faculty;
import me.zhukov.hogwarts.service.impl.FacultyService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {
    private static long facultyCounter = 1;
    private final Map<Long, Faculty> facultyRepository = new HashMap<>();


    @PostConstruct
    public void init() {
        addFaculty(new Faculty("Гриффиндор", "Красный"));
        addFaculty(new Faculty("Когтевран", "Синий"));
        addFaculty(new Faculty("Пуффендуй", "Желтый"));
        addFaculty(new Faculty("Слизерин", "Зеленый"));
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(facultyCounter++);
        facultyRepository.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty removeFaculty(long id) {
        return facultyRepository.remove(id);
    }

    @Override
    public Faculty findFaculty(long id) {
        return facultyRepository.get(id);
    }

    @Override
    public void updateFaculty(long id, Faculty facultyForUpdate) {
        facultyForUpdate.setId(id);
        facultyRepository.put(id, facultyForUpdate);
    }

    @Override
    public List<Faculty> getAllByColor(String color) {
        return facultyRepository.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .toList();
    }
}
