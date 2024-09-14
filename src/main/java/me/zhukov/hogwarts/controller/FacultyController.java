package me.zhukov.hogwarts.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.zhukov.hogwarts.model.Faculty;
import me.zhukov.hogwarts.model.Student;
import me.zhukov.hogwarts.service.impl.FacultyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Контроллер факультетов")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/add")
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @DeleteMapping("/{id}/remove")
    public Faculty removeFaculty(@PathVariable("id") long id) {
        return facultyService.removeFaculty(id);
    }

    @GetMapping("/{id}/get")
    public Faculty findFacultyById(@PathVariable("id") long id) {
        return facultyService.findFaculty(id);
    }

    @PutMapping("/{id}/update")
    public void updateFaculty(@PathVariable("id") long id,
                              @RequestBody Faculty faculty) {
        facultyService.updateFaculty(id, faculty);
    }

    @GetMapping("/get/by-color")
    public List<Faculty> getAllByColor(@RequestParam("color") String color) {
        return facultyService.getAllByColor(color);
    }
    @GetMapping("/get/by-color-or-name")
    public List<Faculty> getFacultyByColorOrName(String colorOrName) {
        return facultyService.getFacultyByColorOrName(colorOrName);
    }
}
